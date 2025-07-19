use clap::Parser;

mod cmd;
use cmd::*;

fn main() {
    let cli = Cli::parse();

    match &cli.command {
        Commands::Run { config } => {
            run(config);
        }
    }
}

fn run(config: &String) {
    println!("Running with config: {}", config);
}
