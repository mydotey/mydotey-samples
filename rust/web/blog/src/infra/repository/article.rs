use super::*;
use crate::{
    models::{Article, NewArticle},
    schema::articles::{self, dsl::*},
};
use anyhow::anyhow;
use diesel::prelude::*;

pub struct ArticleRepository<'a> {
    connection: &'a mut SqliteConnection,
}

impl<'a> ArticleRepository<'a> {
    pub fn new(connection: &'a mut SqliteConnection) -> Self {
        ArticleRepository { connection }
    }
}

impl<'a> Repository for ArticleRepository<'a> {
    type NewModel = NewArticle;
    type QueryModel = Article;

    fn create(&mut self, model: Self::NewModel) -> anyhow::Result<Self::QueryModel> {
        diesel::insert_into(articles::table)
            .values(&model)
            .execute(self.connection)
            .map_err(|e| anyhow!("Error inserting new article: {}", e))?;

        articles
            .order(id.desc())
            .first(self.connection)
            .map_err(|e| anyhow!("Error retrieving newly created article: {}", e))
    }

    fn find_by_id(&mut self, value: i32) -> anyhow::Result<Option<Self::QueryModel>> {
        articles
            .filter(id.eq(value))
            .first::<Self::QueryModel>(self.connection)
            .optional()
            .map_err(|e| anyhow!("Error finding article by id {}: {}", value, e))
    }
}
