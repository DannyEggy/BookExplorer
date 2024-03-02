package com.danny.bookexplorer.search_result;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.danny.bookexplorer.adapter.search_result_adapter.BookResultAdapter;
import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.api.ElasticClient;
import com.danny.bookexplorer.api.huggingface.ModelAPI;
import com.danny.bookexplorer.api.huggingface.ModelClient;
import com.danny.bookexplorer.databinding.ActivitySearchResultBooksBinding;
import com.danny.bookexplorer.model.Book;
import com.danny.bookexplorer.model.BookSource;
import com.danny.bookexplorer.model.Hit;
import com.danny.bookexplorer.model.SearchResult;
import com.danny.bookexplorer.repository.NetworkState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultBooks extends AppCompatActivity {

    private ActivitySearchResultBooksBinding binding;
    private ResultDetailsRepository detailsRepository;
    private String queryHibrid;
    private String query="";

    private String knnField;
    private String vector;
    private double[] knnQueryVector;
//    private double[] knnQueryVector = {-0.058046817779541016, 0.06355021893978119, -0.038580894470214844, 0.02695302851498127, -0.0815589651465416, -0.031529635190963745, 0.06309529393911362, 0.010260204784572124, 0.023370157927274704, -0.09161992371082306, -0.00898708961904049, -0.031924422830343246, -0.052477769553661346, -0.009691180661320686, -0.0042710998095571995, 0.01215401105582714, 0.031040938571095467, 0.03326805308461189, -0.03265811502933502, -0.02857097238302231, -0.014808092266321182, 0.04437794163823128, 0.062012072652578354, -0.028411349281668663, 0.020538734272122383, -0.034220851957798004, 0.028311166912317276, 0.005890721920877695, -0.06618569046258926, -0.07520480453968048, -0.018046915531158447, -0.03254372999072075, -0.07521216571331024, 0.02096959762275219, -0.07296346873044968, -0.015631282702088356, 0.007919030264019966, 0.08554615825414658, 0.10891687124967575, 0.001465596491470933, -0.05214395001530647, -0.09630636125802994, -0.07247219234704971, 0.04277884587645531, -0.008195885457098484, 0.000903322477824986, -0.005930421873927116, 0.045707818120718, -0.018416395410895348, 0.02868887595832348, -0.013885182328522205, -0.005102910101413727, -0.05372154712677002, 0.0005632060929201543, 0.07688728719949722, -0.005879020318388939, 0.0692007765173912, 0.02277839556336403, 0.0902731865644455, 0.06296415627002716, -0.025200624018907547, -0.013141578063368797, -0.04614434018731117, 0.08403120934963226, 0.03781375288963318, -0.1025780513882637, 7.775698759360239e-05, 0.027707519009709358, -0.10450375080108643, -0.012952778488397598, -0.035535778850317, 0.007542291656136513, 0.10584437847137451, -0.06458307802677155, 0.024122482165694237, -0.020986195653676987, -0.057246580719947815, -0.00928691029548645, 0.01029033213853836, 0.005959140136837959, 0.011280263774096966, 0.012632324360311031, -0.09104277193546295, 0.08691965043544769, 0.020423613488674164, 0.011197705753147602, 0.011907437816262245, 0.015635110437870026, -0.07564280927181244, 0.024380631744861603, -0.025461889803409576, 0.00961589440703392, -0.041209086775779724, 0.08613214641809464, -0.05889775604009628, 0.08260264992713928, 0.08863223344087601, -0.03561060503125191, -0.10803847759962082, 0.23200589418411255, 0.007893257774412632, -0.020390044897794724, 0.11189845949411392, 0.013232996687293053, 0.019116396084427834, -0.022124312818050385, 0.012154992669820786, -0.07248584181070328, 0.06580688059329987, 0.024051493033766747, -0.024605682119727135, -0.06945803761482239, -0.07255134731531143, -0.023052966222167015, 0.05977459251880646, -0.046262238174676895, 0.010507314465939999, 0.0402960442006588, -0.011669740080833435, -0.026154672726988792, 0.0011374263558536768, 0.09074777364730835, -0.037358127534389496, 0.008916384540498257, -0.05822160467505455, 0.007447465322911739, 0.02691705897450447, -6.018515280504773e-33, -0.018051406368613243, -0.014825030229985714, 0.007585946936160326, 0.0009766350267454982, -0.02584311179816723, 0.023472821339964867, -0.07878023386001587, 0.033969946205616, -0.03814753144979477, 0.03489166870713234, 0.00908555369824171, -0.06496144086122513, -0.08064883202314377, -0.016657698899507523, 0.017334846779704094, 0.06841544061899185, -0.04158513993024826, 0.01735673099756241, -0.006211829371750355, 0.007024477701634169, -0.04073231667280197, 0.06614763289690018, -0.012498992495238781, 0.06299193948507309, -0.08096835017204285, -0.033343978226184845, -0.037162598222494125, -0.06864427030086517, -0.007435865234583616, 0.02360326610505581, 0.05580323562026024, 0.04873209074139595, -0.014238574542105198, -0.06415855884552002, -0.06090572103857994, -0.09064283967018127, -0.029521852731704712, -0.10355550795793533, 0.013638656586408615, -0.09360545128583908, -0.009573354385793209, 0.017301354557275772, 0.005422608461230993, -0.026671307161450386, -0.0795406773686409, 0.02391430176794529, 0.004516714718192816, 0.036836616694927216, 0.009033686481416225, 0.06342308968305588, 0.021094975993037224, -0.03506754711270332, -0.05070246383547783, -0.024370482191443443, -0.06690338253974915, 0.050683945417404175, 0.01009028498083353, 0.015585899353027344, 0.061608728021383286, -0.032840851694345474, 0.03151944279670715, 0.09466475248336792, 0.061344217509031296, 0.03232530131936073, -0.042332008481025696, -0.042487334460020065, -0.0011980546405538917, -0.03704865649342537, 0.0022510599810630083, 0.00929111335426569, -0.08395978808403015, 0.05071747303009033, 0.0322328582406044, -0.08766110986471176, -0.0694490596652031, -0.022318948060274124, 0.014408675022423267, 0.09058372676372528, -0.0562426783144474, -0.05109831318259239, 0.022249870002269745, 0.060925353318452835, 0.008598249405622482, -0.019789909943938255, 0.09903062134981155, -0.028358038514852524, -0.09015249460935593, -0.029458532109856606, -0.03717337176203728, 0.10059051215648651, 0.01139833964407444, -0.037778470665216446, -0.04664802551269531, -0.04008963331580162, 0.007168133277446032, 2.8540736515026283e-33, -0.0007152754697017372, -0.027557499706745148, 0.05257304385304451, -0.02151832915842533, 0.05311739072203636, -0.028455855324864388, -0.04665157571434975, 0.009215688332915306, -0.0020764460787177086, -0.015327338129281998, -0.05557344853878021, 0.012978346087038517, 0.10811904817819595, 0.01319089625030756, 0.08035765588283539, 0.032712530344724655, -0.003238084726035595, -0.0022562253288924694, 0.03280816599726677, 0.053631965070962906, -0.01002409029752016, -0.006304461043328047, -0.0005344883538782597, -0.06085674837231636, -0.031712185591459274, 0.014303527772426605, 0.017770348116755486, -0.022107362747192383, -0.006210123188793659, 0.028199736028909683, 0.05101235583424568, -0.020988501608371735, -0.0757041722536087, -0.07271146774291992, -0.0027628003153949976, 0.018687887117266655, 0.004203648306429386, 0.002632666379213333, -0.01023218035697937, -0.07722631096839905, 0.11744694411754608, 0.016384147107601166, 0.006574111059308052, 0.03590709716081619, 0.07541648298501968, 0.0038182761054486036, 0.04779496416449547, 0.058163996785879135, 0.026079021394252777, 0.07771991938352585, -0.049636900424957275, 0.06578454375267029, -0.05830724537372589, -0.01321343518793583, 0.04421450197696686, 0.029013056308031082, -0.01415629405528307, -0.03692178428173065, 0.02902979589998722, -0.0488249696791172, -0.10843326151371002, 0.035519763827323914, 0.041354693472385406, 0.03806537762284279, -0.03082207590341568, 0.06392958015203476, -0.08333803713321686, 0.09656334668397903, -0.01842459850013256, -0.0338468924164772, 0.08060192316770554, 0.029393130913376808, -0.07010573893785477, -0.037179313600063324, -0.04530535265803337, 0.010749343782663345, 0.005503756459802389, 0.05726083740592003, -0.0858103334903717, 0.06606993079185486, -0.021941684186458588, 0.08435309678316116, 0.0043958998285233974, 0.05075308308005333, -0.034337662160396576, -0.044733207672834396, 0.05493737384676933, 0.024380207061767578, -0.023395484313368797, -0.045757751911878586, 0.12524408102035522, 0.009175049141049385, 0.07126232981681824, -0.05479750037193298, -0.01186025608330965, -1.2832777507298943e-08, -0.08084676414728165, 0.047754477709531784, 0.04592832922935486, -0.0506712831556797, 0.0838930532336235, -0.04227133095264435, -0.008836107328534126, 0.05345327407121658, 0.035913653671741486, 0.042156122624874115, 0.09950671344995499, 0.04025940224528313, 0.02708006650209427, 0.009957083500921726, 0.06772089749574661, -0.035531941801309586, -0.0770263746380806, -0.04587075486779213, 0.023775815963745117, 0.06387366354465485, -0.06387937813997269, -0.025711677968502045, 0.04888731986284256, 0.04869183525443077, 0.009845622815191746, 0.030349763110280037, 0.006868953816592693, -0.11171973496675491, 0.044608715921640396, 0.01613842509686947, 0.05355216562747955, 0.05591936782002449, -0.030874690040946007, -0.061163175851106644, -0.049411945044994354, -0.05331497639417648, -0.014059467241168022, -0.021441111341118813, 0.05176125094294548, -0.0012338744709268212, 0.005550064612179995, -0.07664934545755386, 0.049911778420209885, 0.009795433841645718, -0.04581659659743309, 0.049850381910800934, 0.037585243582725525, -0.06471773236989975, 0.014201167970895767, -0.05750782787799835, -0.09588806331157684, 0.04329747334122658, 0.0627613514661789, -0.007233387790620327, 0.050001733005046844, 0.006653776858001947, 0.01512887142598629, 0.05217337608337402, -0.056688323616981506, -0.03163391351699829, 0.14295902848243713, 0.030080057680606842, 0.04164273664355278, 0.03533976525068283};

    private SearchResultViewModel viewModel;

    private SearchResultViewModel viewModelHybridSearch;

    private SearchResultViewModel viewModelMultipleSearch;
    private List<Hit> hitList;

    private String queryTitle;
    private String queryDesc;
    private int pageCountGTE;
    private int pageCountLTE;
    private double averageRatingGTE;
    private double averageRatingLTE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ElasticAPI elasticAPI = ElasticClient.getElasticAPIClient();
        detailsRepository = new ResultDetailsRepository(elasticAPI);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        query = intent.getStringExtra("queryNormal");
        queryHibrid = intent.getStringExtra("query");
        knnField = intent.getStringExtra("knnField");

        queryTitle = intent.getStringExtra("queryTitle");
        queryDesc = intent.getStringExtra("queryDesc");
        pageCountGTE = intent.getIntExtra("pageCountGTE", 0);
        pageCountLTE = intent.getIntExtra("pageCountLTE", 1000);
        averageRatingGTE = intent.getDoubleExtra("averageRatingGTE", 1.0);
        averageRatingLTE = intent.getDoubleExtra("averageRatingLTE", 5.0);

        vector = intent.getStringExtra("knnQueryVector");



//        Toast.makeText(this, Arrays.toString(vector), Toast.LENGTH_LONG).show();
//        knnQueryVector = {-0.058046817779541016, 0.06355021893978119, -0.038580894470214844, 0.02695302851498127, -0.0815589651465416, -0.031529635190963745, 0.06309529393911362, 0.010260204784572124, 0.023370157927274704, -0.09161992371082306, -0.00898708961904049, -0.031924422830343246, -0.052477769553661346, -0.009691180661320686, -0.0042710998095571995, 0.01215401105582714, 0.031040938571095467, 0.03326805308461189, -0.03265811502933502, -0.02857097238302231, -0.014808092266321182, 0.04437794163823128, 0.062012072652578354, -0.028411349281668663, 0.020538734272122383, -0.034220851957798004, 0.028311166912317276, 0.005890721920877695, -0.06618569046258926, -0.07520480453968048, -0.018046915531158447, -0.03254372999072075, -0.07521216571331024, 0.02096959762275219, -0.07296346873044968, -0.015631282702088356, 0.007919030264019966, 0.08554615825414658, 0.10891687124967575, 0.001465596491470933, -0.05214395001530647, -0.09630636125802994, -0.07247219234704971, 0.04277884587645531, -0.008195885457098484, 0.000903322477824986, -0.005930421873927116, 0.045707818120718, -0.018416395410895348, 0.02868887595832348, -0.013885182328522205, -0.005102910101413727, -0.05372154712677002, 0.0005632060929201543, 0.07688728719949722, -0.005879020318388939, 0.0692007765173912, 0.02277839556336403, 0.0902731865644455, 0.06296415627002716, -0.025200624018907547, -0.013141578063368797, -0.04614434018731117, 0.08403120934963226, 0.03781375288963318, -0.1025780513882637, 7.775698759360239e-05, 0.027707519009709358, -0.10450375080108643, -0.012952778488397598, -0.035535778850317, 0.007542291656136513, 0.10584437847137451, -0.06458307802677155, 0.024122482165694237, -0.020986195653676987, -0.057246580719947815, -0.00928691029548645, 0.01029033213853836, 0.005959140136837959, 0.011280263774096966, 0.012632324360311031, -0.09104277193546295, 0.08691965043544769, 0.020423613488674164, 0.011197705753147602, 0.011907437816262245, 0.015635110437870026, -0.07564280927181244, 0.024380631744861603, -0.025461889803409576, 0.00961589440703392, -0.041209086775779724, 0.08613214641809464, -0.05889775604009628, 0.08260264992713928, 0.08863223344087601, -0.03561060503125191, -0.10803847759962082, 0.23200589418411255, 0.007893257774412632, -0.020390044897794724, 0.11189845949411392, 0.013232996687293053, 0.019116396084427834, -0.022124312818050385, 0.012154992669820786, -0.07248584181070328, 0.06580688059329987, 0.024051493033766747, -0.024605682119727135, -0.06945803761482239, -0.07255134731531143, -0.023052966222167015, 0.05977459251880646, -0.046262238174676895, 0.010507314465939999, 0.0402960442006588, -0.011669740080833435, -0.026154672726988792, 0.0011374263558536768, 0.09074777364730835, -0.037358127534389496, 0.008916384540498257, -0.05822160467505455, 0.007447465322911739, 0.02691705897450447, -6.018515280504773e-33, -0.018051406368613243, -0.014825030229985714, 0.007585946936160326, 0.0009766350267454982, -0.02584311179816723, 0.023472821339964867, -0.07878023386001587, 0.033969946205616, -0.03814753144979477, 0.03489166870713234, 0.00908555369824171, -0.06496144086122513, -0.08064883202314377, -0.016657698899507523, 0.017334846779704094, 0.06841544061899185, -0.04158513993024826, 0.01735673099756241, -0.006211829371750355, 0.007024477701634169, -0.04073231667280197, 0.06614763289690018, -0.012498992495238781, 0.06299193948507309, -0.08096835017204285, -0.033343978226184845, -0.037162598222494125, -0.06864427030086517, -0.007435865234583616, 0.02360326610505581, 0.05580323562026024, 0.04873209074139595, -0.014238574542105198, -0.06415855884552002, -0.06090572103857994, -0.09064283967018127, -0.029521852731704712, -0.10355550795793533, 0.013638656586408615, -0.09360545128583908, -0.009573354385793209, 0.017301354557275772, 0.005422608461230993, -0.026671307161450386, -0.0795406773686409, 0.02391430176794529, 0.004516714718192816, 0.036836616694927216, 0.009033686481416225, 0.06342308968305588, 0.021094975993037224, -0.03506754711270332, -0.05070246383547783, -0.024370482191443443, -0.06690338253974915, 0.050683945417404175, 0.01009028498083353, 0.015585899353027344, 0.061608728021383286, -0.032840851694345474, 0.03151944279670715, 0.09466475248336792, 0.061344217509031296, 0.03232530131936073, -0.042332008481025696, -0.042487334460020065, -0.0011980546405538917, -0.03704865649342537, 0.0022510599810630083, 0.00929111335426569, -0.08395978808403015, 0.05071747303009033, 0.0322328582406044, -0.08766110986471176, -0.0694490596652031, -0.022318948060274124, 0.014408675022423267, 0.09058372676372528, -0.0562426783144474, -0.05109831318259239, 0.022249870002269745, 0.060925353318452835, 0.008598249405622482, -0.019789909943938255, 0.09903062134981155, -0.028358038514852524, -0.09015249460935593, -0.029458532109856606, -0.03717337176203728, 0.10059051215648651, 0.01139833964407444, -0.037778470665216446, -0.04664802551269531, -0.04008963331580162, 0.007168133277446032, 2.8540736515026283e-33, -0.0007152754697017372, -0.027557499706745148, 0.05257304385304451, -0.02151832915842533, 0.05311739072203636, -0.028455855324864388, -0.04665157571434975, 0.009215688332915306, -0.0020764460787177086, -0.015327338129281998, -0.05557344853878021, 0.012978346087038517, 0.10811904817819595, 0.01319089625030756, 0.08035765588283539, 0.032712530344724655, -0.003238084726035595, -0.0022562253288924694, 0.03280816599726677, 0.053631965070962906, -0.01002409029752016, -0.006304461043328047, -0.0005344883538782597, -0.06085674837231636, -0.031712185591459274, 0.014303527772426605, 0.017770348116755486, -0.022107362747192383, -0.006210123188793659, 0.028199736028909683, 0.05101235583424568, -0.020988501608371735, -0.0757041722536087, -0.07271146774291992, -0.0027628003153949976, 0.018687887117266655, 0.004203648306429386, 0.002632666379213333, -0.01023218035697937, -0.07722631096839905, 0.11744694411754608, 0.016384147107601166, 0.006574111059308052, 0.03590709716081619, 0.07541648298501968, 0.0038182761054486036, 0.04779496416449547, 0.058163996785879135, 0.026079021394252777, 0.07771991938352585, -0.049636900424957275, 0.06578454375267029, -0.05830724537372589, -0.01321343518793583, 0.04421450197696686, 0.029013056308031082, -0.01415629405528307, -0.03692178428173065, 0.02902979589998722, -0.0488249696791172, -0.10843326151371002, 0.035519763827323914, 0.041354693472385406, 0.03806537762284279, -0.03082207590341568, 0.06392958015203476, -0.08333803713321686, 0.09656334668397903, -0.01842459850013256, -0.0338468924164772, 0.08060192316770554, 0.029393130913376808, -0.07010573893785477, -0.037179313600063324, -0.04530535265803337, 0.010749343782663345, 0.005503756459802389, 0.05726083740592003, -0.0858103334903717, 0.06606993079185486, -0.021941684186458588, 0.08435309678316116, 0.0043958998285233974, 0.05075308308005333, -0.034337662160396576, -0.044733207672834396, 0.05493737384676933, 0.024380207061767578, -0.023395484313368797, -0.045757751911878586, 0.12524408102035522, 0.009175049141049385, 0.07126232981681824, -0.05479750037193298, -0.01186025608330965, -1.2832777507298943e-08, -0.08084676414728165, 0.047754477709531784, 0.04592832922935486, -0.0506712831556797, 0.0838930532336235, -0.04227133095264435, -0.008836107328534126, 0.05345327407121658, 0.035913653671741486, 0.042156122624874115, 0.09950671344995499, 0.04025940224528313, 0.02708006650209427, 0.009957083500921726, 0.06772089749574661, -0.035531941801309586, -0.0770263746380806, -0.04587075486779213, 0.023775815963745117, 0.06387366354465485, -0.06387937813997269, -0.025711677968502045, 0.04888731986284256, 0.04869183525443077, 0.009845622815191746, 0.030349763110280037, 0.006868953816592693, -0.11171973496675491, 0.044608715921640396, 0.01613842509686947, 0.05355216562747955, 0.05591936782002449, -0.030874690040946007, -0.061163175851106644, -0.049411945044994354, -0.05331497639417648, -0.014059467241168022, -0.021441111341118813, 0.05176125094294548, -0.0012338744709268212, 0.005550064612179995, -0.07664934545755386, 0.049911778420209885, 0.009795433841645718, -0.04581659659743309, 0.049850381910800934, 0.037585243582725525, -0.06471773236989975, 0.014201167970895767, -0.05750782787799835, -0.09588806331157684, 0.04329747334122658, 0.0627613514661789, -0.007233387790620327, 0.050001733005046844, 0.006653776858001947, 0.01512887142598629, 0.05217337608337402, -0.056688323616981506, -0.03163391351699829, 0.14295902848243713, 0.030080057680606842, 0.04164273664355278, 0.03533976525068283};


//        Toast.makeText(this, query+ " " + knnField , Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, knnQueryVector, Toast.LENGTH_SHORT).show();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(type.equals("hybridSearch")){
            knnQueryVector = getVectorFromString(vector);
            viewModelHybridSearch = getHybridSearchResultViewModel(queryHibrid, knnField, knnQueryVector);
            viewModelHybridSearch.getHybridSearchResult().observe(this, this::bindResultUI);
            viewModelHybridSearch.getNetworkState().observe(this, networkState -> {
                int visibility = (networkState == NetworkState.LOADING) ? View.VISIBLE : View.GONE;
                binding.progressBarSearchResult.setVisibility(visibility);
                binding.txtErrorSearchResult.setVisibility((networkState == NetworkState.ERROR) ? View.VISIBLE : View.GONE);
            });
        } else if (type.equals("multipleSearch")) {
            viewModelMultipleSearch = getMultipleSearchResultViewModel(queryTitle, queryDesc,
                    pageCountGTE, pageCountLTE, averageRatingGTE, averageRatingLTE);
            viewModelMultipleSearch.getMultipleSearchResult().observe(this, this::bindResultUI);
            viewModelMultipleSearch.getNetworkState().observe(this, networkState -> {
                int visibility = (networkState == NetworkState.LOADING) ? View.VISIBLE : View.GONE;
                binding.progressBarSearchResult.setVisibility(visibility);
                binding.txtErrorSearchResult.setVisibility((networkState == NetworkState.ERROR) ? View.VISIBLE : View.GONE);
            });
        } else{
            viewModel = getSearchResultViewModel(query);
//
//
//
//
            viewModel.getSearchResult().observe(this, this::bindResultUI);
            viewModel.getNetworkState().observe(this, networkState -> {
                int visibility = (networkState == NetworkState.LOADING) ? View.VISIBLE : View.GONE;
                binding.progressBarSearchResult.setVisibility(visibility);
                binding.txtErrorSearchResult.setVisibility((networkState == NetworkState.ERROR) ? View.VISIBLE : View.GONE);
            });
        }






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void bindResultUI(SearchResult searchResult) {
        hitList = searchResult.getHits().getHits();

        BookResultAdapter adapter = new BookResultAdapter(hitList);
        binding.rcvResult.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rcvResult.setAdapter(adapter);

    }

    public SearchResultViewModel getSearchResultViewModel(String query) {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SearchResultViewModel(detailsRepository, query);
            }
        }).get(SearchResultViewModel.class);
    }

    public SearchResultViewModel getHybridSearchResultViewModel(String query, String knnField, double[] knnQueryVector) {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SearchResultViewModel(detailsRepository, query, knnField, knnQueryVector);
            }
        }).get(SearchResultViewModel.class);
    }

    public SearchResultViewModel getMultipleSearchResultViewModel(String queryTitle, String queryDesc,
                                                                  int pageCountGTE, int pageCountLTE,
                                                                  double averageRatingGTE, double averageRatingLTE) {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SearchResultViewModel(detailsRepository, queryTitle, queryDesc, pageCountGTE,
                        pageCountLTE, averageRatingGTE, averageRatingLTE);
            }
        }).get(SearchResultViewModel.class);
    }


    public double[] getVectorFromString(String knnQueryVector){
        // Loại bỏ các ký tự không cần thiết từ chuỗi


        String cleanInput = knnQueryVector.replace("[ ", "").replace("]", "").replace("  ", " ").replace("[", "");
        String[] stringArray = cleanInput.split(" ");
        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = stringArray[i].replace(" ", "");
        }
        Log.d("StringArray: ", stringArray[0]);


        // Chuyển đổi từ mảng chuỗi sang mảng số double
        double[] result = new double[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            result[i] = Double.parseDouble(stringArray[i]);
        }


        return result;
    }



}