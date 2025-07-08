pub fn max_sum_of_sub_sequence(nums: &[i32]) -> i32 {
    if nums.is_empty() {
        return 0;
    }
    if nums.len() == 1 {
        return nums[0];
    }
    let max = max_sum_of_sub_sequence(&nums[0..nums.len() - 1]);
    let mut sum = nums[nums.len() - 1];
    for i in (0..nums.len() - 1).rev() {
        if nums[i] < 0 {
            break;
        }
        sum += nums[i];
    }
    if sum > max { sum } else { max }
}

pub fn min_sum_of_sub_sequence(nums: &[i32]) -> i32 {
    if nums.is_empty() {
        return 0;
    }
    if nums.len() == 1 {
        return nums[0];
    }
    let min = min_sum_of_sub_sequence(&nums[0..nums.len() - 1]);
    let mut sum = nums[nums.len() - 1];
    for i in (0..nums.len() - 1).rev() {
        if nums[i] > 0 {
            break;
        }
        sum += nums[i];
    }
    if sum < min { sum } else { min }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_max_sum_sub_sequence() {
        let nums = vec![1, 2, 3, 4, 5];
        assert_eq!(max_sum_of_sub_sequence(&nums), 15);
        let nums = vec![5, 4, 3, 2, 1];
        assert_eq!(max_sum_of_sub_sequence(&nums), 15);
        let nums = vec![1, -2, 3, -4, 5];
        assert_eq!(max_sum_of_sub_sequence(&nums), 5);
        let nums = vec![-1, -2, -3];
        assert_eq!(max_sum_of_sub_sequence(&nums), -1);
    }

    #[test]
    fn test_min_sum_sub_sequence() {
        let nums = vec![1, 2, 3, 4, 5];
        assert_eq!(min_sum_of_sub_sequence(&nums), 1);
        let nums = vec![5, 4, 3, 2, 1];
        assert_eq!(min_sum_of_sub_sequence(&nums), 1);
        let nums = vec![1, -2, 3, -4, 5];
        assert_eq!(min_sum_of_sub_sequence(&nums), -4);
        let nums = vec![1, -2, -3, -4, 5];
        assert_eq!(min_sum_of_sub_sequence(&nums), -9);
    }
}
