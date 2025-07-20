use crate::schema::*;
use anyhow::anyhow;
use diesel::prelude::*;
use std::env;

use crate::conf;

pub fn get_connection() -> anyhow::Result<SqliteConnection> {
    let config = conf::get_config()?;
    SqliteConnection::establish(&config.db.url)
        .map_err(|e| anyhow!("Error connecting to database: {}", e))
}
