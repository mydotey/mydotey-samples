use anyhow::anyhow;
use log::warn;
use std::sync::{
    RwLock,
    atomic::{AtomicBool, Ordering},
};

static APP_CONFIG: RwLock<Option<Config>> = RwLock::new(None);

pub fn init(config_path: &str) -> anyhow::Result<()> {
    static INIT: AtomicBool = AtomicBool::new(false);
    if let Err(_) = INIT.compare_exchange(false, true, Ordering::SeqCst, Ordering::SeqCst) {
        warn!("Configuration already initialized");
        return Ok(());
    }

    let app_config = config::Config::builder()
        .add_source(config::File::new(config_path, config::FileFormat::Yaml))
        .build()?
        .try_deserialize::<Config>()?;
    APP_CONFIG
        .write()
        .map_err(|e| anyhow!("{}", e.to_string()))?
        .replace(app_config);
    Ok(())
}

pub fn get_config() -> anyhow::Result<Config> {
    APP_CONFIG
        .read()
        .map_err(|e| anyhow!("{}", e.to_string()))?
        .clone()
        .ok_or_else(|| anyhow!("Configuration not initialized"))
}

#[derive(Debug, Clone, serde::Deserialize)]
pub struct Config {
    pub web: Web,
    pub db: Db,
}

#[derive(Debug, Clone, serde::Deserialize)]
pub struct Web {
    pub server: ServerConfig,
}

#[derive(Debug, Clone, serde::Deserialize)]
pub struct ServerConfig {
    pub host: String,
    pub port: u16,
}

impl ServerConfig {
    pub fn to_addr(&self) -> (String, u16) {
        (self.host.clone(), self.port)
    }
}

#[derive(Debug, Clone, serde::Deserialize)]
pub struct Db {
    pub sqlite: String,
}
