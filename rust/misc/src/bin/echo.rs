use std::io::*;

fn main() -> Result<()> {
    let args = std::env::args().skip(1).collect::<Vec<_>>();
    println!("Arguments: {:?}", args);
    println!();

    println!("Echo start!");
    let stdin = stdin();
    let mut stdout = stdout();
    loop {
        println!();
        print!("Please input something: ");
        stdout.flush()?;

        let mut input = String::new();
        stdin.read_line(&mut input)?;
        println!("Echo: {}", input.trim());
        if input == "exit\n" || input == "quit\n" {
            println!();
            break;
        }
    }

    println!("Echo end!");
    Ok(())
}
