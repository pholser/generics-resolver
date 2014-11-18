package ru.vyarus.java.generics.resolver

import ru.vyarus.guice.persist.orient.support.finder.generics.*
import ru.vyarus.java.generics.resolver.context.GenericsInfo
import ru.vyarus.java.generics.resolver.context.GenericsInfoFactory
import ru.vyarus.java.generics.resolver.support.Base1
import ru.vyarus.java.generics.resolver.support.Base2
import ru.vyarus.java.generics.resolver.support.BeanBase
import ru.vyarus.java.generics.resolver.support.BeanRoot
import ru.vyarus.java.generics.resolver.support.ComplexGenerics
import ru.vyarus.java.generics.resolver.support.ComplexGenerics2
import ru.vyarus.java.generics.resolver.support.Lvl2Base1
import ru.vyarus.java.generics.resolver.support.Lvl2Base2
import ru.vyarus.java.generics.resolver.support.Lvl2Base3
import ru.vyarus.java.generics.resolver.support.Lvl2BeanBase
import ru.vyarus.java.generics.resolver.support.Model
import ru.vyarus.java.generics.resolver.support.OtherModel
import ru.vyarus.java.generics.resolver.support.Root
import spock.lang.Specification

import java.lang.reflect.ParameterizedType

/**
 * @author Vyacheslav Rusakov 
 * @since 16.10.2014
 */
class GenericsInfoFactoryTest extends Specification {

    def "Check generics resolution"() {

        when: "analyzing finders hierarchy"
        GenericsInfo info = GenericsInfoFactory.create(Root)
        then: "correct generic values resolved"
        info.rootClass == Root
        info.composingTypes.size() == 7
        info.getTypeGenerics(Base1) == ['T': Model]
        info.getTypeGenerics(Base2) == ['K': Model, 'P': OtherModel]
        info.getTypeGenerics(Lvl2Base1) == ['I': Model]
        info.getTypeGenerics(Lvl2Base2) == ['J': Model]
        info.getTypeGenerics(Lvl2Base3) == ['R': Model]
        info.getTypeGenerics(ComplexGenerics)['T'] == Model
        info.getTypeGenerics(ComplexGenerics)['K'] instanceof ParameterizedType
        ((ParameterizedType) info.getTypeGenerics(ComplexGenerics)['K']).getRawType() == List
        info.getTypeGenerics(ComplexGenerics2)['T'] == Model[]

        when: "analyzing bean finders hierarchy"
        info = GenericsInfoFactory.create(BeanRoot)
        then: "correct generic values resolved"
        info.rootClass == BeanRoot
        info.composingTypes.size() == 3
        info.getTypeGenerics(BeanBase) == ['T': Model]
        info.getTypeGenerics(Lvl2BeanBase) == ['I': Model]
        info.getTypeGenerics(Lvl2Base1) == ['I': Model]
    }
}
