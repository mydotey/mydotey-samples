use crate::schema::*;
use anyhow::{Ok, anyhow};
use diesel::prelude::*;
use rbatis::RBatis;
use rbdc_sqlite::SqliteDriver;
use std::{
    env,
    pin::pin,
    sync::RwLock,
    task::{Context, Poll, Waker},
};

use crate::conf;

pub fn get_connection() -> anyhow::Result<SqliteConnection> {
    let config = conf::get_config()?;
    SqliteConnection::establish(&config.db.url)
        .map_err(|e| anyhow!("Error connecting to database: {}", e))
}

pub fn get_rbatis() -> anyhow::Result<RBatis> {
    static RBATIS: RwLock<Option<RBatis>> = RwLock::new(None);
    let lock = RBATIS.read().map_err(|e| anyhow!("{}", e.to_string()))?;
    match lock.as_ref() {
        Some(rb) => return Ok(rb.clone()),
        None => drop(lock),
    }

    let mut lock = RBATIS.write().map_err(|e| anyhow!("{}", e.to_string()))?;
    if lock.is_some() {
        return Ok(lock.as_ref().unwrap().clone());
    }
    let config = conf::get_config()?;
    let rbatis = RBatis::new();
    let waker = Waker::noop();
    let mut cx = Context::from_waker(waker);
    let mut future = pin!(rbatis.link(SqliteDriver {}, &config.db.sqlite));
    loop {
        if let Poll::Ready(r) = future.as_mut().poll(&mut cx) {
            r.map_err(|e| anyhow!("Failed to connect to database: {}", e))?;
            break;
        }
    }
    lock.replace(rbatis.clone());

    Ok(lock.as_ref().unwrap().clone())
}
