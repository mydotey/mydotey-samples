pub fn hanoi(n: usize, source: &str, target: &str, auxiliary: &str) {
    if n == 0 {
        return;
    }
    if n == 1 {
        println!("{} => {}", source, target);
        return;
    }

    hanoi(n - 1, source, auxiliary, target);
    println!("{} => {}", source, target);
    hanoi(n - 1, auxiliary, target, source);
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_hanoi() {
        let n = 3;
        let source = "A";
        let target = "C";
        let auxiliary = "B";
        hanoi(n, source, target, auxiliary);
        // Expected output:
        // Move disk 1 from A to C
        // Move disk 2 from A to B
        // Move disk 1 from C to B
        // Move disk 3 from A to C
        // Move disk 1 from B to A
        // Move disk 2 from B to C
        // Move disk 1 from A to C
    }
}
