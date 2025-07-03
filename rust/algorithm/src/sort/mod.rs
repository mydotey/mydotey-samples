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
    use std::ptr::read;

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

    #[test]
    fn test_drop() {
        let x = TestStruct { value: 10 };
        let _ = x;
        println!("TestStruct created with value: {:?}", x);
        let y = x;
        println!("TestStruct created with value: {:?}", y);
    }

    #[test]
    fn test_drop2() {
        let test_struct = TestStruct { value: 42 };
        println!("TestStruct created with value: {}", test_struct.value);
        let _ = test_struct; // Ensure the struct is dropped at the end of the scope
        println!("TestStruct has been dropped.");
        drop(test_struct);
        println!("TestStruct has been dropped.");
    }

    #[test]
    fn test_drop3() {
        let mut test_struct = TestStruct { value: 42 };
        println!("TestStruct created with value: {}", test_struct.value);

        unsafe {
            let y = &test_struct as *const TestStruct;
            let z = read(y);
            println!("Read TestStruct from pointer: {:?}", z);
            test_struct = z;
        }

        println!("TestStruct will be dropped now: {:?}.", test_struct);
    }

    #[test]
    fn test_drop4() {
        let mut test_structs = [
            TestStruct { value: 1 },
            TestStruct { value: 2 },
            TestStruct { value: 3 },
        ];
        println!("TestStruct created with value: {:?}", test_structs);

        unsafe {
            let y = &test_structs[1] as *const TestStruct;
            let z = read(y);
            println!("Read TestStruct from pointer: {:?}", z);
            test_structs[0] = z;
        }

        println!("TestStruct will be dropped now: {:?}.", test_structs);
    }

    #[test]
    fn test_drop5() {
        let mut test_structs = [
            TestStruct { value: 1 },
            TestStruct { value: 2 },
            TestStruct { value: 3 },
        ];
        println!("TestStruct created with values: {:?}", test_structs);
        test_structs.swap(0, 1);
        println!("TestStruct will be dropped now: {:?}.", test_structs);
    }

    #[test]
    fn test_drop6() {
        let mut x = TestStruct { value: 10 };
        println!("TestStruct created with value: {}", x.value);
        x = TestStruct { value: 30 };
        println!("TestStruct updated with value: {}", x.value);
    }

    #[derive(Debug)]
    struct TestStruct {
        value: i32,
    }

    impl Drop for TestStruct {
        fn drop(&mut self) {
            println!("Dropping TestStruct with value: {}", self.value);
        }
    }
}
