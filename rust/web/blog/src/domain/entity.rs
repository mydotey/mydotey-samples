pub trait Entity {
    fn get_id(&self) -> Option<u64>;
    fn set_id(&mut self, id: Option<u64>);
    fn get_created_by(&self) -> Option<u64>;
    fn set_created_by(&mut self, created_by: Option<u64>);
    fn get_updated_by(&self) -> Option<u64>;
    fn set_updated_by(&mut self, updated_by: Option<u64>);
    fn get_create_time(&self) -> Option<u64>;
    fn set_create_time(&mut self, create_time: Option<u64>);
    fn get_update_time(&self) -> Option<u64>;
    fn set_update_time(&mut self, update_time: Option<u64>);
    fn is_deleted(&self) -> Option<bool>;
    fn set_deleted(&mut self, deleted: Option<bool>);
}

#[macro_export]
macro_rules! impl_entity {
    ($type:ty) => {
        impl crate::domain::Entity for $type {
            fn get_id(&self) -> Option<u64> {
                self.id
            }

            fn set_id(&mut self, id: Option<u64>) {
                self.id = id;
            }

            fn get_created_by(&self) -> Option<u64> {
                self.created_by
            }

            fn set_created_by(&mut self, created_by: Option<u64>) {
                self.created_by = created_by;
            }

            fn get_updated_by(&self) -> Option<u64> {
                self.updated_by
            }

            fn set_updated_by(&mut self, updated_by: Option<u64>) {
                self.updated_by = updated_by;
            }

            fn get_create_time(&self) -> Option<u64> {
                self.create_time
            }

            fn set_create_time(&mut self, create_time: Option<u64>) {
                self.create_time = create_time;
            }

            fn get_update_time(&self) -> Option<u64> {
                self.update_time
            }

            fn set_update_time(&mut self, update_time: Option<u64>) {
                self.update_time = update_time;
            }

            fn is_deleted(&self) -> Option<bool> {
                self.deleted
            }

            fn set_deleted(&mut self, deleted: Option<bool>) {
                self.deleted = deleted;
            }
        }
    };
}
