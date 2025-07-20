mod article;
pub use article::*;

pub trait Repository {
    type NewModel;
    type QueryModel;

    fn create(&mut self, model: Self::NewModel) -> anyhow::Result<Self::QueryModel>;
    fn find_by_id(&mut self, id: i32) -> anyhow::Result<Option<Self::QueryModel>>;
}
