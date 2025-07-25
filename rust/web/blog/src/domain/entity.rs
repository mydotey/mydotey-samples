use actix_web::test;
use serde::{Deserialize, Serialize};
use w_macro::entity_fields;

#[entity_fields]
#[derive(Debug, Clone, Serialize, Deserialize, Default)]
pub struct Article {
    pub title: String,
    pub body: String,
    pub published: bool,
}

#[cfg(test)]
mod tests {
    use super::*;
    use actix_web::web::Data;

    #[actix_web::test]
    async fn test_article_entity() {
        let article = Article::default();
        println!("{:?}", article);
        assert!(article.id.is_none());
        assert!(article.created_by.is_none());
        assert!(article.updated_by.is_none());
        assert!(article.create_time.is_none());
        assert!(article.update_time.is_none());
        assert!(article.deleted.is_none());
        assert!(article.title.is_empty());
        assert!(article.body.is_empty());
        assert!(!article.published);
    }
}
