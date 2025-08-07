use w_macro::{repository, repository_factory};

use crate::domain::content::{Article, ArticleRepository};

repository!(Article);

impl ArticleRepository for DefaultArticleRepository {}

repository_factory!(Article);
