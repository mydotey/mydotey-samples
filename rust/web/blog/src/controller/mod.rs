use std::sync::LazyLock;

use actix_web::dev::{HttpServiceFactory, ServiceFactory, ServiceRequest};
use actix_web::web;
use actix_web::{App, Error};

mod article;

pub fn config<T>(app: App<T>) -> App<T>
where
    T: ServiceFactory<ServiceRequest, Config = (), Error = Error, InitError = ()>,
{
    app.service(article::hello)
        .service(article::echo)
        .service(article::create_article)
        .route("/hey", web::get().to(article::manual_hello))
}
