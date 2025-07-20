use diesel::prelude::*;
use serde::{Deserialize, Serialize};

#[derive(Queryable, Selectable, Serialize, Deserialize)]
#[diesel(table_name = crate::schema::articles)]
#[diesel(check_for_backend(diesel::sqlite::Sqlite))]
pub struct Article {
    pub id: i32,
    pub title: String,
    pub body: String,
    pub published: bool,
}

#[derive(Insertable, Serialize, Deserialize)]
#[diesel(table_name = crate::schema::articles)]
pub struct NewArticle {
    pub title: String,
    pub body: String,
}
