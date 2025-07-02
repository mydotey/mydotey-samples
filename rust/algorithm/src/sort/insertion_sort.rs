use super::ArrayInsert;

pub fn insertion_sort<T: PartialOrd>(arr: &mut [T]) {
    _insertion_sort(arr, 0, arr.len());
}

fn _insertion_sort<T: PartialOrd>(arr: &mut [T], start: usize, end: usize) {
    if end - start <= 1 {
        return;
    }

    _insertion_sort(arr, start, end - 1);

    for i in start..end - 1 {
        if arr[i] <= arr[end - 1] {
            continue;
        }

        arr.insert(end - 1, i);
        break;
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_insertion_sort() {
        let mut arr = [64, 34, 25, 12, 22, 11, 90];
        insertion_sort(&mut arr);
        assert_eq!(arr, [11, 12, 22, 25, 34, 64, 90]);
    }
}
