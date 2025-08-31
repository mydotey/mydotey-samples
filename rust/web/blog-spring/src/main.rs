use spring::{App, auto_config};
use spring_web::{WebConfigurator, WebPlugin, axum::response::IntoResponse, extractor::Path};
use spring_web::{get, route};

pub mod framework;
pub mod modules;

#[auto_config(WebConfigurator)]
#[tokio::main]
async fn main() {
    App::new().add_plugin(WebPlugin).run().await
}

#[get("/")]
async fn hello_world() -> impl IntoResponse {
    "hello world"
}

#[route("/hello/{name}", method = "GET", method = "POST")]
async fn hello(Path(name): Path<String>) -> impl IntoResponse {
    format!("hello {name}")
}
