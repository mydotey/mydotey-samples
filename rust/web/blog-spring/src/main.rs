use spring::{App, auto_config};
use spring_web::get;
use spring_web::{WebConfigurator, WebPlugin, axum::response::IntoResponse};

pub mod framework;
pub mod modules;

#[auto_config(WebConfigurator)]
#[tokio::main]
async fn main() {
    App::new().add_plugin(WebPlugin).run().await
}

#[get("/health")]
async fn health() -> impl IntoResponse {
    "ok"
}
