use lang_extension::slice::SliceExtention;

pub fn insertion_sort<T: PartialOrd>(arr: &mut [T]) {
    _insertion_sort(arr, 0, arr.len());
}

fn _insertion_sort<T: PartialOrd>(arr: &mut [T], start: usize, end: usize) {
    if end - start <= 1 {
        return;
    }

    _insertion_sort(arr, start, end - 1);

    let f: fn(&[T], usize, usize, usize) -> usize = if (end - start) % 2 == 0 {
        search
    } else {
        binary_search
    };

    let i = f(arr, end - 1, start, end - 1);
    arr.lift(end - 1, i);
}

fn search<T: PartialOrd>(arr: &[T], value_index: usize, start: usize, end: usize) -> usize {
    for i in start..end {
        if arr[i] <= arr[value_index] {
            continue;
        }
        return i;
    }

    return end;
}

fn binary_search<T: PartialOrd>(arr: &[T], value_index: usize, start: usize, end: usize) -> usize {
    let len = end - start;
    if len <= 1 {
        return end;
    }

    let mid = start + end / 2;
    if arr[mid] > arr[value_index] {
        search(arr, value_index, start, mid)
    } else {
        search(arr, value_index, mid + 1, end)
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
