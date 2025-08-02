use proc_macro::TokenStream;
use quote::{format_ident, quote};
use syn::parse::Parser;
use syn::{ItemStruct, parse, parse_macro_input};

use darling::FromMeta;

#[derive(Debug, FromMeta, Default)]
#[darling(derive_syn_parse)]
struct EntityFieldArgs {
    name: String,
    ty: String,
}

#[proc_macro_attribute]
pub fn entity_field(args: TokenStream, input: TokenStream) -> TokenStream {
    let mut item_struct = parse_macro_input!(input as ItemStruct);
    let EntityFieldArgs { name, ty } = match syn::parse(args) {
        Ok(v) => v,
        Err(e) => {
            return e.to_compile_error().into();
        }
    };
    let name = format_ident!("{}", name);
    let ty = format_ident!("{}", ty);
    if let syn::Fields::Named(ref mut fields) = item_struct.fields {
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub #name: Option<#ty> })
                .unwrap(),
        );
    }

    return quote! {
        #item_struct
    }
    .into();
}

#[proc_macro_attribute]
pub fn entity_fields(args: TokenStream, input: TokenStream) -> TokenStream {
    let mut item_struct = parse_macro_input!(input as ItemStruct);
    let _ = parse_macro_input!(args as parse::Nothing);

    if let syn::Fields::Named(ref mut fields) = item_struct.fields {
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub id: Option<w_ddd::entity::EntityID> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub created_by: Option<w_ddd::entity::EntityID> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub updated_by: Option<w_ddd::entity::EntityID> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub create_time: Option<w_ddd::entity::EntityTime> })
                .unwrap(),
        );
        fields.named.push(
            syn::Field::parse_named
                .parse2(quote! { pub update_time: Option<w_ddd::entity::EntityTime> })
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

#[proc_macro_derive(Entity)]
pub fn derive_entity(item: TokenStream) -> TokenStream {
    let item_struct = parse_macro_input!(item as ItemStruct);
    let type_name = item_struct.ident;
    quote! {
        impl w_ddd::entity::Entity for #type_name {
            fn get_id(&self) -> Option<w_ddd::entity::EntityID> {
                self.id
            }

            fn set_id(&mut self, id: Option<w_ddd::entity::EntityID>) {
                self.id = id;
            }

            fn get_created_by(&self) -> Option<w_ddd::entity::EntityID> {
                self.created_by
            }

            fn set_created_by(&mut self, created_by: Option<w_ddd::entity::EntityID>) {
                self.created_by = created_by;
            }

            fn get_updated_by(&self) -> Option<w_ddd::entity::EntityID> {
                self.updated_by
            }

            fn set_updated_by(&mut self, updated_by: Option<w_ddd::entity::EntityID>) {
                self.updated_by = updated_by;
            }

            fn get_create_time(&self) -> Option<w_ddd::entity::EntityTime> {
                self.create_time
            }

            fn set_create_time(&mut self, create_time: Option<w_ddd::entity::EntityTime>) {
                self.create_time = create_time;
            }

            fn get_update_time(&self) -> Option<w_ddd::entity::EntityTime> {
                self.update_time
            }

            fn set_update_time(&mut self, update_time: Option<u64>) {
                self.update_time = update_time;
            }

            fn is_deleted(&self) -> Option<bool> {
                self.deleted
            }

            fn set_deleted(&mut self, deleted: Option<bool>) {
                self.deleted = deleted;
            }
        }
    }
    .into()
}
