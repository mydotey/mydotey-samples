use w_macro::{repository, repository_factory};

use crate::domain::content::{Article, ArticleRepository};

repository!(ty = "Article");

impl ArticleRepository for DefaultArticleRepository {}

repository_factory!(ty = "Article");
