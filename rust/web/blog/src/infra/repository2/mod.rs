use std::{
    any,
    pin::pin,
    task::{Context, Poll, Waker},
};

use anyhow::{Result, anyhow};
use rbatis::{RBatis, crud, executor::Executor};
use rbs::value;
use w_ddd::{entity::Entity, repository::Repository};

use crate::domain::content::{Article, ArticleRepository};

#[macro_export]
macro_rules! repository {
    ($e:tt, $e_t:tt, $repo:tt, $impl_repo:tt) => {
        crud!($e_t {});

        pub fn $e() -> anyhow::Result<Box<dyn $repo>> {
            Ok(Box::new($impl_repo {
                rbatis: crate::infra::db::get_rbatis()?,
            }))
        }

        struct $impl_repo {
            rbatis: RBatis,
        }

        impl $impl_repo {
            fn executor(&self) -> &dyn Executor {
                &self.rbatis
            }

            fn table_name(&self) -> &'static str {
                any::type_name::<Self>()
            }
        }

        impl $repo for $impl_repo {}

        impl Repository<$e_t> for $impl_repo {
            fn create(&self, entity: $e_t) -> anyhow::Result<$e_t> {
                let mut entity = entity;
                let now = chrono::Local::now().timestamp();
                entity.set_create_time(Some(now));
                entity.set_update_time(Some(now));
                entity.set_version(Some(0));
                let waker = Waker::noop();
                let mut cx = Context::from_waker(waker);
                let mut future = pin!($e_t::insert(self.executor(), &entity));
                loop {
                    if let Poll::Ready(r) = future.as_mut().poll(&mut cx) {
                        return r
                            .map(|r| {
                                let mut e = entity.clone();
                                e.set_id(r.last_insert_id.as_i64());
                                return e;
                            })
                            .map_err(|e| {
                                anyhow!("Failed to create record of {}: {}", self.table_name(), e)
                            });
                    }
                }
            }

            fn read(&self, id: w_ddd::entity::EntityID) -> anyhow::Result<$e_t> {
                let waker = Waker::noop();
                let mut cx = Context::from_waker(waker);
                let mut future = pin!($e_t::select_by_map(self.executor(), value! { "id": id }));
                loop {
                    if let Poll::Ready(r) = future.as_mut().poll(&mut cx) {
                        let r = r.map_err(|e| {
                            anyhow!("Failed to read record of {}: {}", self.table_name(), e)
                        })?;
                        return if r.is_empty() {
                            Err(anyhow!(
                                "record of {} with id {} not found",
                                self.table_name(),
                                id
                            ))
                        } else if r.len() > 1 {
                            Err(anyhow!(
                                "{} records of {} found with id {}",
                                r.len(),
                                self.table_name(),
                                id
                            ))
                        } else {
                            Ok(r[0].clone())
                        };
                    }
                }
            }

            fn update(&self, entity: $e_t) -> anyhow::Result<()> {
                let waker = Waker::noop();
                let mut cx = Context::from_waker(waker);
                let mut future = pin!($e_t::update_by_map(
                    self.executor(),
                    &entity,
                    value! { "id": entity.get_id()}
                ));
                loop {
                    if let Poll::Ready(r) = future.as_mut().poll(&mut cx) {
                        let r = r.map_err(|e| {
                            anyhow!("Failed to read record of {}: {}", self.table_name(), e)
                        })?;
                        return match r.rows_affected {
                            0 => Err(anyhow!(
                                "record of {} with id {:?} not found",
                                self.table_name(),
                                entity.get_id()
                            )),
                            1 => Ok(()),
                            _ => Err(anyhow!(
                                "{} records of {} found with id {:?}",
                                r.rows_affected,
                                self.table_name(),
                                entity.get_id()
                            )),
                        };
                    }
                }
            }

            fn delete(&self, id: w_ddd::entity::EntityID) -> anyhow::Result<()> {
                let waker = Waker::noop();
                let mut cx = Context::from_waker(waker);
                let mut future = pin!($e_t::delete_by_map(self.executor(), value! { "id": id}));
                loop {
                    if let Poll::Ready(r) = future.as_mut().poll(&mut cx) {
                        let r = r.map_err(|e| {
                            anyhow!(
                                "Failed to delete record of {}: {}",
                                any::type_name::<$e_t>(),
                                e
                            )
                        })?;
                        return match r.rows_affected {
                            0 | 1 => Ok(()),
                            _ => Err(anyhow!(
                                "{} records of {} found with id {}",
                                r.rows_affected,
                                self.table_name(),
                                id
                            )),
                        };
                    }
                }
            }
        }
    };
}

repository!(
    article,
    Article,
    ArticleRepository,
    DefaultArticleRepository
);
