package fr.dawan.training.tools;


import org.modelmapper.ModelMapper;



public class DtoTool {
	
	//Lien DOC
	
	private static ModelMapper mapper = new ModelMapper();
	
	/*
	public static Product convertToProduct(ProductDto dto) {
		return mapper.map(dto, Product.class);
	}
	
	public static ProductDto convertToProductDto(Product p) {
		return mapper.map(p, ProductDto.class);
	}
	*/
	
	//Définir une méthode générique
	
	public static <TSource, TDestination> TDestination convert(TSource obj, Class<TDestination> clazz) {
		
		/*
		 * On peut ajouter des règles personnalisées
		 */
		/*
		mapper.typeMap(Product.class, ProductDto.class)
		.addMappings(m -> 
		m.map(src -> src.getDescription(), ProductDto::setDescription)
				);
				*/
		
		return mapper.map(obj, clazz);
	}

}
