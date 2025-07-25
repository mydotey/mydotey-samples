use proc_macro::TokenStream;
use quote::quote;
use syn::parse::Parser;
use syn::{ItemStruct, parse, parse_macro_input};

#[proc_macro_attribute]
pub fn entity_fields(args: TokenStream, input: TokenStream) -> TokenStream {
    let mut item_struct = parse_macro_input!(input as ItemStruct);
    let _ = parse_macro_input!(args as parse::Nothing);

    if let syn::Fields::Named(ref mut fields) = item_struct.fields {
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub id: Option<u64> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub created_by: Option<u64> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub updated_by: Option<u64> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub create_time: Option<u64> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub update_time: Option<u64> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub deleted: Option<bool> })
                .unwrap(),
        );
    }

    return quote! {
        #item_struct
    }
    .into();
}
