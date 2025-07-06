pub fn exist<T: PartialOrd>(arr: &[T], target: &T) -> bool {
    if arr.len() == 0 {
        return false;
    }

    let mid = arr.len() / 2;
    if &arr[mid] == target {
        return true;
    } else if &arr[mid] > target {
        return exist(&arr[0..mid], target);
    } else {
        return exist(&arr[mid + 1..arr.len()], target);
    }
}

pub fn binary_search<T: PartialOrd>(arr: &[T], target: &T) -> Option<usize> {
    return _binary_search(arr, target, 0, arr.len());
}

fn _binary_search<T: PartialOrd>(arr: &[T], target: &T, start: usize, end: usize) -> Option<usize> {
    let len = end - start;
    if len == 0 {
        return None;
    }

    let mid = start + len / 2;
    if arr[mid] == *target {
        return Some(mid);
    } else if arr[mid] > *target {
        return _binary_search(arr, target, start, mid);
    } else {
        return _binary_search(arr, target, mid + 1, end);
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_binary_search() {
        let arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
        assert_eq!(binary_search(&arr, &5), Some(4));
        assert_eq!(binary_search(&arr, &11), None);
        assert_eq!(binary_search(&arr, &1), Some(0));
        assert_eq!(binary_search(&arr, &10), Some(9));
        assert_eq!(binary_search(&[], &1), None);
        assert_eq!(binary_search(&[1], &1), Some(0));
    }

    #[test]
    fn test_exist() {
        let arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
        assert!(exist(&arr, &5));
        assert!(!exist(&arr, &11));
        assert!(exist(&arr, &1));
        assert!(exist(&arr, &10));
    }
}
