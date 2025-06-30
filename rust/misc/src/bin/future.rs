#[tokio::main]
async fn main() {
    // This is a simple example of using async/await in Rust with Tokio.
    let result = hello().await;
    println!("Result: {:?}", result);
}

async fn hello() {
    println!("Hello, world!");
}
