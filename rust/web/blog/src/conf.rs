#[derive(Debug, Clone, serde::Deserialize)]
pub struct Config {
    pub web: Web,
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
