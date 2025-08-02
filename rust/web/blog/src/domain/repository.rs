pub trait EntityRepository<T> {
    fn create(&self, entity: T) -> Result<T, String>;
    fn read(&self, id: u64) -> Result<T, String>;
    fn update(&self, entity: T) -> Result<T, String>;
    fn delete(&self, id: u64) -> Result<(), String>;
}
