use std::any;

use crate::infra::db;
use crate::infra::repository::*;
use crate::models::*;

pub fn create_article(model: NewArticle) -> anyhow::Result<Article> {
    let mut connection = db::get_connection()?;
    let mut repository = ArticleRepository::new(&mut connection);
    repository.create(model)
}
