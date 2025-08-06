use w_macro::repository;

use crate::domain::content::{Article, ArticleRepository};

repository!(ty = "Article", method = "article");
