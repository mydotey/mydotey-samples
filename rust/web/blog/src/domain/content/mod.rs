use actix_web::test;
use serde::{Deserialize, Serialize};
use w_ddd::repository::Repository;
use w_macro::{Entity, entity_field, entity_fields};

#[entity_fields]
#[entity_field(name = "title", ty = "String")]
#[entity_field(name = "body", ty = "String")]
#[entity_field(name = "published", ty = "bool")]
#[derive(Debug, Clone, Serialize, Deserialize, Default, Entity)]
pub struct Article {}

pub trait ArticleRepository: Repository<Article> {}

#[cfg(test)]
mod tests {
    use super::*;
    use actix_web::web::Data;
    use w_ddd::entity::Entity;

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
        assert!(article.title.is_none());
        assert!(article.body.is_none());
        assert!(article.published.is_none());
        assert!(article.get_version().is_none());
        assert!(article.is_deleted().is_none());
    }
}
