use clap::{Parser, Subcommand};

#[derive(Parser, Debug)]
#[command(version, about, long_about = None, arg_required_else_help = true)]
pub struct Cli {
    /// Name of the person to greet
    #[command(subcommand)]
    pub command: Commands,

    #[arg(short, long)]
    pub misc: Option<String>,
}

#[derive(Subcommand, Debug)]
pub enum Commands {
    Run {
        #[arg(
            short,
            long,
            default_value = "config.yaml",
            help = "Path to the configuration file",
            value_name = "FILE"
        )]
        config: String,
    },
    Hello {
        #[arg(
            short,
            long,
            default_value = "World",
            help = "Name to greet",
            value_name = "NAME"
        )]
        name: String,
    },
    Hello2(Hello2),
}

#[derive(Parser, Debug)]
pub struct Hello2 {
    #[arg(
        short,
        long,
        default_value = "World",
        help = "Name to greet",
        value_name = "NAME"
    )]
    pub name: String,
}
