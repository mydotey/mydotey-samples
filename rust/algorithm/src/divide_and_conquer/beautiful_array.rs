use std::vec;

pub fn beautiful_array(n: i32) -> Vec<i32> {
    return match n {
        0 => vec![],
        1 => vec![1],
        2 => vec![1, 2],
        _ => {
            let result = beautiful_array(n - 1);
            'f1: for i in 0..n - 1 {
                let mut result2 = result.clone();
                result2.insert(i as usize, n);
                for j in 1..(n as usize) - 1 {
                    for k in 0..j {
                        for l in j + 1..(n as usize) {
                            if result2[k] + result2[l] == result2[j] * 2 {
                                continue 'f1;
                            }
                        }
                    }
                }
                return result2;
            }
            panic!("No beautiful array found for n = {}", n);
        }
    };
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_beautiful_array() {
        assert_eq!(beautiful_array(0), vec![]);
        assert_eq!(beautiful_array(1), vec![1]);
        assert_eq!(beautiful_array(2), vec![1, 2]);
        println!("{:?}", beautiful_array(3));
        println!("{:?}", beautiful_array(4));
        println!("{:?}", beautiful_array(5));
    }
}
