package cz.muni.fi.timeline.api;

import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BeanMappingServiceImpl implements BeanMappingService {

	private final Mapper dozer;

    public BeanMappingServiceImpl(Mapper dozer) {
        this.dozer = dozer;
    }

    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    public  <T> T mapTo(Object u, Class<T> mapToClass)
    {
        return dozer.map(u, mapToClass);
    }

    public Mapper getMapper(){
    	return dozer;
    }
}
