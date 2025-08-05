use std::any;

use crate::infra::db;
use crate::infra::repository::*;
use crate::models::*;

use crate::domain::content::{Article as Article2, ArticleRepository as ArticleRepository2};

pub fn create_article(model: NewArticle) -> anyhow::Result<Article> {
    let mut connection = db::get_connection()?;
    let mut repository = ArticleRepository::new(&mut connection);
    repository.create(model)
}

pub fn create_article2(model: Article2) -> anyhow::Result<Article2> {
    let repository = crate::infra::repository2::article()?;
    repository.create(model)
}
