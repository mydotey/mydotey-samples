use proc_macro::TokenStream;
use quote::{format_ident, quote};
use syn::parse::Parser;
use syn::{ItemStruct, parse, parse_macro_input};

use darling::FromMeta;

#[derive(Debug, FromMeta, Default)]
#[darling(derive_syn_parse)]
struct EntityFieldArgs {
    name: String,
    ty: String,
}

#[proc_macro_attribute]
pub fn entity_field(args: TokenStream, input: TokenStream) -> TokenStream {
    let mut item_struct = parse_macro_input!(input as ItemStruct);
    let EntityFieldArgs { name, ty } = match syn::parse(args) {
        Ok(v) => v,
        Err(e) => {
            return e.to_compile_error().into();
        }
    };
    let name = format_ident!("{}", name);
    let ty = format_ident!("{}", ty);
    if let syn::Fields::Named(ref mut fields) = item_struct.fields {
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub #name: Option<#ty> })
                .unwrap(),
        );
    }

    return quote! {
        #item_struct
    }
    .into();
}

#[proc_macro_attribute]
pub fn entity_fields(args: TokenStream, input: TokenStream) -> TokenStream {
    let mut item_struct = parse_macro_input!(input as ItemStruct);
    let _ = parse_macro_input!(args as parse::Nothing);

    if let syn::Fields::Named(ref mut fields) = item_struct.fields {
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub id: Option<w_ddd::entity::EntityID> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub created_by: Option<w_ddd::entity::EntityID> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub updated_by: Option<w_ddd::entity::EntityID> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub create_time: Option<w_ddd::entity::EntityTime> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub update_time: Option<w_ddd::entity::EntityTime> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub deleted: Option<bool> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub version: Option<u64> })
                .unwrap(),
        );
    }

    return quote! {
        #item_struct
    }
    .into();
}

#[proc_macro_derive(Entity)]
pub fn derive_entity(item: TokenStream) -> TokenStream {
    let item_struct = parse_macro_input!(item as ItemStruct);
    let type_name = item_struct.ident;
    quote! {
        impl w_ddd::entity::Entity for #type_name {
            fn get_id(&self) -> Option<w_ddd::entity::EntityID> {
                self.id
            }

            fn set_id(&mut self, id: Option<w_ddd::entity::EntityID>) {
                self.id = id;
            }

            fn get_created_by(&self) -> Option<w_ddd::entity::EntityID> {
                self.created_by
            }

            fn set_created_by(&mut self, created_by: Option<w_ddd::entity::EntityID>) {
                self.created_by = created_by;
            }

            fn get_updated_by(&self) -> Option<w_ddd::entity::EntityID> {
                self.updated_by
            }

            fn set_updated_by(&mut self, updated_by: Option<w_ddd::entity::EntityID>) {
                self.updated_by = updated_by;
            }

            fn get_create_time(&self) -> Option<w_ddd::entity::EntityTime> {
                self.create_time
            }

            fn set_create_time(&mut self, create_time: Option<w_ddd::entity::EntityTime>) {
                self.create_time = create_time;
            }

            fn get_update_time(&self) -> Option<w_ddd::entity::EntityTime> {
                self.update_time
            }

            fn set_update_time(&mut self, update_time: Option<w_ddd::entity::EntityTime>) {
                self.update_time = update_time;
            }

            fn is_deleted(&self) -> Option<bool> {
                self.deleted
            }

            fn set_deleted(&mut self, deleted: Option<bool>) {
                self.deleted = deleted;
            }

            fn get_version(&self) -> Option<u64> {
                self.version
            }

            fn set_version(&mut self, version: Option<u64>) {
                self.version = version;
            }
        }
    }
    .into()
}

#[derive(Debug, FromMeta, Default)]
#[darling(derive_syn_parse)]
struct RepositoryArgs {
    ty: String,
    method: String,
}

#[proc_macro]
pub fn repository(input: TokenStream) -> TokenStream {
    let RepositoryArgs { ty, method } = match syn::parse(input) {
        Ok(v) => v,
        Err(e) => {
            return e.to_compile_error().into();
        }
    };
    let method = format_ident!("{}", method);
    let entity_type = format_ident!("{}", ty);
    let repo = format_ident!("{}Repository", ty);
    let impl_repo = format_ident!("Default{}Repository", ty);

    quote! {
        rbatis::crud!(#entity_type {});

        pub fn #method() -> anyhow::Result<Box<dyn #repo>> {
            Ok(Box::new(#impl_repo {
                rbatis: crate::infra::db::get_rbatis()?,
            }))
        }

        struct #impl_repo {
            rbatis: rbatis::RBatis,
        }

        impl #impl_repo {
            fn executor(&self) -> &dyn rbatis::executor::Executor {
                &self.rbatis
            }

            fn table_name(&self) -> &'static str {
                std::any::type_name::<Self>()
            }
        }

        impl #repo for #impl_repo {}

        impl w_ddd::repository::Repository<#entity_type> for #impl_repo {
            fn create(&self, entity: #entity_type) -> anyhow::Result<#entity_type> {
                let mut entity = entity;
                let now = chrono::Local::now().timestamp();
                entity.create_time = Some(now);
                entity.update_time = Some(now);
                entity.version = Some(0);
                let waker = std::task::Waker::noop();
                let mut cx = std::task::Context::from_waker(waker);
                let mut future = std::pin::pin!(#entity_type::insert(self.executor(), &entity));
                loop {
                    if let std::task::Poll::Ready(r) = future.as_mut().poll(&mut cx) {
                        return r
                            .map(|r| {
                                let mut e = entity.clone();
                                e.id = r.last_insert_id.as_i64();
                                return e;
                            })
                            .map_err(|e| {
                                anyhow::anyhow!("Failed to create record of {}: {}", self.table_name(), e)
                            });
                    }
                }
            }

            fn read(&self, id: w_ddd::entity::EntityID) -> anyhow::Result<#entity_type> {
                let waker = std::task::Waker::noop();
                let mut cx = std::task::Context::from_waker(waker);
                let mut future = std::pin::pin!(#entity_type::select_by_map(self.executor(), rbs::value! { "id": id }));
                loop {
                    if let std::task::Poll::Ready(r) = future.as_mut().poll(&mut cx) {
                        let r = r.map_err(|e| {
                            anyhow::anyhow!("Failed to read record of {}: {}", self.table_name(), e)
                        })?;
                        return if r.is_empty() {
                            Err(anyhow::anyhow!(
                                "record of {} with id {} not found",
                                self.table_name(),
                                id
                            ))
                        } else if r.len() > 1 {
                            Err(anyhow::anyhow!(
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

            fn update(&self, entity: #entity_type) -> anyhow::Result<()> {
                let waker = std::task::Waker::noop();
                let mut cx = std::task::Context::from_waker(waker);
                let mut future = std::pin::pin!(#entity_type::update_by_map(
                    self.executor(),
                    &entity,
                    rbs::value! { "id": entity.id}
                ));
                loop {
                    if let std::task::Poll::Ready(r) = future.as_mut().poll(&mut cx) {
                        let r = r.map_err(|e| {
                            anyhow::anyhow!("Failed to read record of {}: {}", self.table_name(), e)
                        })?;
                        return match r.rows_affected {
                            0 => Err(anyhow::anyhow!(
                                "record of {} with id {:?} not found",
                                self.table_name(),
                                entity.id
                            )),
                            1 => Ok(()),
                            _ => Err(anyhow::anyhow!(
                                "{} records of {} found with id {:?}",
                                r.rows_affected,
                                self.table_name(),
                                entity.id
                            )),
                        };
                    }
                }
            }

            fn delete(&self, id: w_ddd::entity::EntityID) -> anyhow::Result<()> {
                let waker = std::task::Waker::noop();
                let mut cx = std::task::Context::from_waker(waker);
                let mut future = std::pin::pin!(#entity_type::delete_by_map(self.executor(), rbs::value! { "id": id}));
                loop {
                    if let std::task::Poll::Ready(r) = future.as_mut().poll(&mut cx) {
                        let r = r.map_err(|e| {
                            anyhow::anyhow!(
                                "Failed to delete record of {}: {}",
                                self.table_name(),
                                e
                            )
                        })?;
                        return match r.rows_affected {
                            0 | 1 => Ok(()),
                            _ => Err(anyhow::anyhow!(
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

    }
    .into()
}
