package com.bio4j.data.test

import org.scalatest.FunSuite
import scala.xml._
import com.bio4j.data._, uniprot._
import com.bio4j.model._

class Bio4jdataimportTest extends FunSuite {

  test("Sample UniProt entry parsing") {

    // we need the namespace!
    val entryXML = <entry xmlns="http://uniprot.org/uniprot" version="24" modified="2015-04-01" created="2011-06-28" dataset="Swiss-Prot">
      <accession>Q6GZV8</accession>
      <name>017L_FRG3G</name>
      <protein>
        <recommendedName>
          <fullName>Uncharacterized protein 017L</fullName>
        </recommendedName>
      </protein>
      <gene>
        <name type="ORF">FV3-017L</name>
      </gene>
      <organism>
        <name type="scientific">Frog virus 3</name>
        <name type="common">isolate Goorha</name>
        <name type="synonym">FV-3</name>
        <dbReference id="654924" type="NCBI Taxonomy"/>
        <lineage>
          <taxon>Viruses</taxon>
          <taxon>dsDNA viruses, no RNA stage</taxon>
          <taxon>Iridoviridae</taxon>
          <taxon>Ranavirus</taxon>
        </lineage>
      </organism>
      <organismHost>
        <name type="scientific">Ambystoma</name>
        <name type="common">mole salamanders</name>
        <dbReference id="8295" type="NCBI Taxonomy"/>
      </organismHost>
      <organismHost>
        <name type="scientific">Hyla versicolor</name>
        <name type="common">chameleon treefrog</name>
        <dbReference id="30343" type="NCBI Taxonomy"/>
      </organismHost>
      <organismHost>
        <name type="scientific">Lithobates pipiens</name>
        <name type="common">Northern leopard frog</name>
        <name type="synonym">Rana pipiens</name>
        <dbReference id="8404" type="NCBI Taxonomy"/>
      </organismHost>
      <organismHost>
        <name type="scientific">Notophthalmus viridescens</name>
        <name type="common">Eastern newt</name>
        <name type="synonym">Triturus viridescens</name>
        <dbReference id="8316" type="NCBI Taxonomy"/>
      </organismHost>
      <organismHost>
        <name type="scientific">Rana sylvatica</name>
        <name type="common">Wood frog</name>
        <dbReference id="45438" type="NCBI Taxonomy"/>
      </organismHost>
      <reference key="1">
        <citation last="84" first="70" volume="323" name="Virology" date="2004" type="journal article">
          <title>Comparative genomic analyses of frog virus 3, type species of the genus Ranavirus (family Iridoviridae).</title>
          <authorList>
            <person name="Tan W.G."/>
            <person name="Barkman T.J."/>
            <person name="Gregory Chinchar V."/>
            <person name="Essani K."/>
          </authorList>
          <dbReference id="15165820" type="PubMed"/>
          <dbReference id="10.1016/j.virol.2004.02.019" type="DOI"/>
        </citation>
        <scope>NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]</scope>
      </reference>
      <dbReference id="AY548484" type="EMBL">
        <property value="AAT09676.1" type="protein sequence ID"/>
        <property value="Genomic_DNA" type="molecule type"/>
      </dbReference>
      <dbReference id="YP_031595.1" type="RefSeq">
        <property value="NC_005946.1" type="nucleotide sequence ID"/>
      </dbReference>
      <dbReference id="2947737" type="GeneID"/>
      <dbReference id="vg:2947737" type="KEGG"/>
      <dbReference id="UP000008770" type="Proteomes"/>
      <proteinExistence type="predicted"/>
      <keyword id="KW-0181">Complete proteome</keyword>
      <keyword id="KW-1185">Reference proteome</keyword>
      <feature description="Uncharacterized protein 017L" id="PRO_0000410557" type="chain">
        <location>
          <begin position="1"/>
          <end position="502"/>
        </location>
      </feature>
      <feature description="Poly-Ala" type="compositionally biased region">
        <location>
          <begin position="425"/>
          <end position="428"/>
        </location>
      </feature>
      <sequence version="1" modified="2004-07-19" checksum="A747EE6F952CBAD7" mass="53469" length="502">METMSDYSKEVSEALSALRGELSALSAAISNTVRAGSYSAPVAKDCKAGHCDSKAVLKSLSRSARDLDSAVEAVSSNCEWASSGYGKQIARALRDDAVRVKREVESTRDAVDVVTPSCCVQGLAEEAGKLSEMAAVYRCMATVFETADSHGVREMLAKVDGLKQTMSGFKRLLGKTAEIDGLSDSVIRLGRSIGEVLPATEGKAMRDLVKQCERLNGLVVDGSRKVEEQCSKLRDMASQSYVVADLASQYDVLGGKAQEALSASDALEQAAAVALRAKAAADAVAKSLDSLDVKKLDRLLEQASAVSGLLAKKNDLDAVVTSLAGLEALVAKKDELYKICAAVNSVDKSKLELLNVKPDRLKSLTEQTVVVSQMTTALATFNEDKLDSVLGKYMQMHRFLGMATQLKLMSDSLAEFQPAKMAQMAAAASQLKDFLTDQTVSRLEKVSAAVDATDVTKYASAFSDGGMVSDMTKAYETVKAFAAVVNSLDSKKLKLVAECAKK</sequence>
    </entry>

    val entry = Entry(entryXML)

    println { entry.features }
  }

  ignore("entry iterator") {

    def entries = Entry.fromUniProtLines( io.Source.fromFile("uniprot_sprot.sample.xml").getLines )

    assert { entries.size == 26 }

    entries foreach { entry => println { entry.accession } }
  }

  ignore("parse all SwissProt") {

    import better.files._

    def entries =
      // Entry.fromUniProtLines( File("../uniprot_sprot.xml").lineIterator )
      Entry.fromUniProtLines( io.Source.fromFile("../uniprot_sprot.xml").getLines )

    def time[T](str: String)(thunk: => T): T = {
      print(str + "... ")
      val t1 = System.currentTimeMillis
      val x = thunk
      val t2 = System.currentTimeMillis
      println((t2 - t1) + " msecs")
      x
    }

    // val accessions = new collection.mutable.HashSet[String]()

    time("parse entries") {

      entries.zipWithIndex foreach {
        case (entry, index) => {

          entry.features.foreach { s => println { s"${entry.accession} ${s._3}" } }
          // val _zz = accessions += entry.accession
          if((index % 10000) == 0) println { s"traversed ${index} entries, current time: ${System.currentTimeMillis}" }
        }
      }
    }

    // assert { accessions.size == 551705 }
  }
}
