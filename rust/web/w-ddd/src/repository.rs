use crate::entity::{Entity, EntityID};
use anyhow::Result;

pub trait Repository<T: Entity> {
    fn create(&self, entity: T) -> Result<T>;
    fn read(&self, id: EntityID) -> Result<T>;
    fn update(&self, entity: T) -> Result<T>;
    fn delete(&self, id: EntityID) -> Result<()>;
}
