package com.example.monpfe.ui.conseil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monpfe.ui.Deplacement.OnReplaceFragment
import com.example.monpfe.R
import kotlinx.android.synthetic.main.fragment_conseil.*


class ConseilFragment(val onReplaceFragment: OnReplaceFragment?) : Fragment() {

    constructor() : this(null){}
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_conseil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(view.context)

        val array = ArrayList<Conseil>()

        array.add(Conseil("Boire un volume d’eau suffisant \n"," - Il est toujours important dans la prise d’un médicament solide (comprimé, gélule) de boire un volume d’eau suffisant, comme un verre d’eau de 200 ml.\n\n" +
                " - L’eau, en tant que liquide est fortement conseillé, en effet d’autres liquides comme le lait, le café, l’alcool ou le jus de pamplemousse le sont beaucoup moins, car ces boissons présentent des protéines et d’autres éléments favorisant le risque d’interactions avec le principe actif du médicament."))
        array.add(Conseil("Conservation \n", "- Il peut arriver qu’un médicament ne comporte aucune indication sur son emballage. Dans ces cas, c’est la conservation à température ambiante qui prévaut. \n \n" +
        "- Certains médicaments, tels que les suppositoires ou les crèmes, sont plus sensibles que d’autres aux changements de température. Leur aspect et leur forme peuvent changer. Si vous constatez un changement de forme, il est préférable de ne plus utiliser le médicament. \n\n" +
        "- Conservez toujours les produits dans leur emballage d’origine avec leur notice d’utilisation."))
        array.add(Conseil("Prendre ses médicaments à la même heure \n", " -  Il est conseillé de prendre ses médicaments (ex. comprimés) toujours à la même heure de la journée (sauf avis contraire du médecin) afin d’avoir une concentration régulière du médicament dans l’organisme et de respecter le cycle chronobiologique du corps, ceci repose sur la pharmacocinétique du médicament.\n \n" +
        "- Pour certains médicaments il est même conseillé de les prendre à une phase précise de la journée (vous référer à la posologie conseillée par le médecin)."))
        array.add(Conseil(" Bien se renseigner sur l’arrêt d’un traitement\n", "- Il est conseillé de bien se renseigner sur l’arrêt de la prise d’un traitement médicamenteux, c’est-à-dire savoir si vous pouvez arrêter un traitement d’un seul coup  ou si vous devez arrêter progressivement \n\n" +
        " - Lors de prescription d’antibiotiques il est important de respecter les conseils du médecin, s’il précise de finir la boîte du médicament il faut absolument le faire même si vous avez l’impression d’être guéri, ceci afin d’éviter le développement de bactéries résistantes."))
        array.add(Conseil("Bien connaître la posologie\n","- Bien connaître la posologie (avant ou après manger), c’est-à-dire la prise de médicament, par exemple faut-il être à jeun ou non et le nombre ou la quantité de médicament qu’il faut prendre.\n\n" +
        " - Certains médicaments sont efficaces avec la prise d’un repas, après ou avant un repas et d’autres à jeun. Lisez toujours la notice d’emballage ou demandez conseil à votre pharmacien pour des informations à ce sujet."))
        array.add(Conseil("Attention avec l’alcool\n", "- Le mélange alcool et médicament est souvent déconseillé, car l’effet du médicament peut être modifié par l’alcool : c’est notamment ères, les tranquillisants et les antidépresseurs. \n\n" +
                "- Parfois un autre effet dangereux de l’alcool peut mener à une augmentation du taux d’alcoolémie, comme par exemple avec certains anti-inflammatoires (type Aspirine®) et peuvent ainsi faire grimper d’un quart le taux d’alcoolémie."))
        array.add(Conseil("Bien conserver ses médicaments\n", "- Il est fortement conseillé de bien conserver ses médicaments au frais et au sec. En effet certains médicaments sont sensibles à ces variations, l’efficacité n’en serait alors plus garantie. En cas de doute demandez conseil à votre pharmacien.\n\n" +
        " - Veuillez également respecter la date d’expiration des médicaments, attention particulièrement avec les médicaments ophtalmiques ou autres préparations qui nécessitent une conservation garantissant la stérilité."))
        recycler_view.adapter = Adapter(array,onReplaceFragment)
    }



}
