use clap::{Parser, Subcommand};

#[derive(Parser, Debug)]
#[command(version, about, long_about = None, arg_required_else_help = true)]
pub struct Cli {
    /// Name of the person to greet
    #[command(subcommand)]
    pub command: Commands,
}

#[derive(Subcommand, Debug)]
pub enum Commands {
    Run {
        #[arg(short, long, default_value_t = String::from("config.yaml"), help = "Path to the configuration file", value_name = "CONFIG_FILE")]
        config: String,
    },
}
