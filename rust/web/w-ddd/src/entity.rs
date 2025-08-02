pub type EntityID = u64;
pub type EntityTime = u64;

pub trait Entity {
    fn get_id(&self) -> Option<EntityID>;
    fn set_id(&mut self, id: Option<EntityID>);
    fn get_created_by(&self) -> Option<EntityID>;
    fn set_created_by(&mut self, created_by: Option<EntityID>);
    fn get_updated_by(&self) -> Option<EntityID>;
    fn set_updated_by(&mut self, updated_by: Option<EntityID>);
    fn get_create_time(&self) -> Option<EntityTime>;
    fn set_create_time(&mut self, create_time: Option<EntityTime>);
    fn get_update_time(&self) -> Option<EntityTime>;
    fn set_update_time(&mut self, update_time: Option<EntityTime>);
    fn is_deleted(&self) -> Option<bool>;
    fn set_deleted(&mut self, deleted: Option<bool>);
}
