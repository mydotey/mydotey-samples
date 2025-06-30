#[global_allocator]
static GLOBAL: mimalloc::MiMalloc = mimalloc::MiMalloc;

mod future;

fn main() {
    let runtime = tokio::runtime::Runtime::new().unwrap();
    runtime.block_on(future::hello());
}
