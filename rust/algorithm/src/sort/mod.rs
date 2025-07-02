mod bubble_sort;
pub use bubble_sort::*;

mod selection_sort;
pub use selection_sort::*;

mod merge_sort;
pub use merge_sort::*;

mod insertion_sort;
pub use insertion_sort::*;

pub trait ArrayInsert {
    fn insert(&mut self, from: usize, to: usize);
}

impl<T: PartialOrd> ArrayInsert for [T] {
    fn insert(&mut self, from: usize, to: usize) {
        if from >= self.len() || to >= self.len() || from == to {
            return;
        }
        if from < to {
            for i in from..to {
                self.swap(i, i + 1);
            }
        } else {
            for i in (to..from).rev() {
                self.swap(i + 1, i);
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_array_insert() {
        let mut arr = [1, 2, 3, 4, 5];
        arr.insert(1, 3);
        assert_eq!(arr, [1, 3, 4, 2, 5]);
        arr.insert(3, 1);
        assert_eq!(arr, [1, 2, 3, 4, 5]);
        arr.insert(4, 0);
        assert_eq!(arr, [5, 1, 2, 3, 4]);
    }
}
