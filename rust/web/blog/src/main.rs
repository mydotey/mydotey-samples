use clap::Parser;

mod cmd;
use cmd::*;

fn main() {
    let cli = Cli::parse();

    match &cli.command {
        Commands::Run { config } => {
            run(config);
        }
        Commands::Hello { name } => {
            hello(name);
        }
        Commands::Hello2(hello2) => {
            hello(&hello2.name);
        }
    }
}

fn run(config: &String) {
    println!("Running with config: {}", config);
}

fn hello(name: &String) {
    println!("Hello, {}!", name);
}
