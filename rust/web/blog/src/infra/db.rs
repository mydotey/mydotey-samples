use crate::schema::*;
use anyhow::anyhow;
use diesel::prelude::*;
use rbatis::RBatis;
use rbdc_sqlite::SqliteDriver;
use std::{
    env,
    pin::pin,
    task::{Context, Poll, Waker},
};

use crate::conf;

pub fn get_connection() -> anyhow::Result<SqliteConnection> {
    let config = conf::get_config()?;
    SqliteConnection::establish(&config.db.url)
        .map_err(|e| anyhow!("Error connecting to database: {}", e))
}

pub fn get_rbatis() -> anyhow::Result<RBatis> {
    let config = conf::get_config()?;
    let db_url = config.db.url.clone();
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
    Ok(rbatis.clone())
}
