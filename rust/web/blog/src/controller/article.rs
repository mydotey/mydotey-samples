use actix_web::{HttpResponse, Responder, get, post, web::Json};

use crate::domain::content::Article;

#[get("/")]
pub async fn hello() -> impl Responder {
    HttpResponse::Ok().body("Hello world!")
}

#[post("/echo")]
pub async fn echo(req_body: String) -> impl Responder {
    HttpResponse::Ok().body(req_body)
}

pub async fn manual_hello() -> impl Responder {
    HttpResponse::Ok().body("Hey there!")
}

#[post("/api/entity/article/create")]
pub async fn create_article(json: Json<Article>) -> impl Responder {
    match crate::service::article::create_article(json.into_inner()) {
        Ok(model) => HttpResponse::Created().json(model),
        Err(e) => {
            log::error!("Failed to create article: {}", e);
            HttpResponse::InternalServerError().body("Failed to create article")
        }
    }
}
