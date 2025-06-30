pub fn selection_sort<T: PartialOrd>(arr: &mut [T]) {
    for i in 0..arr.len() - 1 {
        let mut min_index = i;
        for j in i + 1..arr.len() {
            if arr[j] < arr[min_index] {
                min_index = j;
            }
        }

        if min_index != i {
            arr.swap(i, min_index);
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_selection_sort() {
        let mut arr = [64, 34, 25, 12, 22, 11, 90];
        selection_sort(&mut arr);
        assert_eq!(arr, [11, 12, 22, 25, 34, 64, 90]);
    }
}
