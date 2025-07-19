mod cmd;
mod conf;
mod route;

use actix_web::{App, HttpServer, web};
use clap::Parser;

use cmd::*;
use config::{Config, File, FileFormat};
use log::{LevelFilter, info};
use simple_logger::SimpleLogger;

#[actix_web::main]
async fn main() -> anyhow::Result<()> {
    SimpleLogger::new()
        .with_level(LevelFilter::Info)
        .with_colors(true)
        .env()
        .init()?;

    let cli = Cli::parse();
    match &cli.command {
        Commands::Run { config } => run(config).await?,
        Commands::Hello { name } => hello(name),
        Commands::Hello2(hello2) => hello(&hello2.name),
    }

    Ok(())
}

async fn run(config: &String) -> anyhow::Result<()> {
    let settings = Config::builder()
        .add_source(File::new(config, FileFormat::Yaml))
        .build()?
        .try_deserialize::<conf::Config>()?;
    let server = HttpServer::new(|| {
        App::new()
            .service(route::hello)
            .service(route::echo)
            .route("/hey", web::get().to(route::manual_hello))
    })
    .bind(settings.web.server.to_addr())?;
    info!("Starting web server at {:?}", settings.web.server.to_addr());
    server.run().await?;
    Ok(())
}

fn hello(name: &String) {
    println!("Hello, {}!", name);
}
