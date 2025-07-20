// @generated automatically by Diesel CLI.

diesel::table! {
    articles (id) {
        id -> Integer,
        title -> Text,
        body -> Text,
        published -> Bool,
    }
}
