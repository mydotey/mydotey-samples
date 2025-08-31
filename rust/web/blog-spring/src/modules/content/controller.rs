use spring_web::{axum::response::IntoResponse, extractor::Path, get, route};

#[get("/api/module/content/hello")]
async fn hello_world() -> impl IntoResponse {
    "hello world"
}

#[route("/hello/{name}", method = "GET", method = "POST")]
async fn hello(Path(name): Path<String>) -> impl IntoResponse {
    format!("hello {name}")
}
