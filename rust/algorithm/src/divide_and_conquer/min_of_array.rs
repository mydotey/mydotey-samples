pub fn min<T: PartialOrd>(nums: &[T]) -> Option<&T> {
    if nums.is_empty() {
        return None;
    }
    if nums.len() == 1 {
        return Some(&nums[0]);
    }
    let min_of_sub = min(&nums[0..nums.len() - 1]);
    min_of_sub.map(|min| {
        if nums[nums.len() - 1] < *min {
            &nums[nums.len() - 1]
        } else {
            min
        }
    })
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_min() {
        let nums = vec![1, 2, 3, 4, 5];
        assert_eq!(min(&nums), Some(&1));
        let nums = vec![5, 4, 3, 2, 1];
        assert_eq!(min(&nums), Some(&1));
        let nums = vec![1, 2, 3, 4, 5, 0];
        assert_eq!(min(&nums), Some(&0));
    }
}
