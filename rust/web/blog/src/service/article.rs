use std::any;

use crate::infra::db;
use crate::infra::repository::*;

use crate::domain::content::Article;

pub fn create_article(model: Article) -> anyhow::Result<Article> {
    let repository = crate::infra::repository::article()?;
    repository.create(model)
}
