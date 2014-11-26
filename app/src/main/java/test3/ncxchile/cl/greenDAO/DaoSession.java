package test3.ncxchile.cl.greenDAO;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import test3.ncxchile.cl.greenDAO.Institucion;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig finalizarActaDaoConfig;
    private final DaoConfig actaDaoConfig;
    private final DaoConfig vehiculoDataDaoConfig;
    private final DaoConfig vehiculoDaoConfig;
    private final DaoConfig fichaEstadoVisualDaoConfig;
    private final DaoConfig estadoVisualDaoConfig;
    private final DaoConfig parqueaderoSummaryDaoConfig;
    private final DaoConfig especiasDaoConfig;
    private final DaoConfig clienteDaoConfig;
    private final DaoConfig parqueaderoDaoConfig;
    private final DaoConfig agrupadorDaoConfig;
    private final DaoConfig direccionDaoConfig;
    private final DaoConfig autoridadDaoConfig;
    private final DaoConfig personaDaoConfig;
    private final DaoConfig correosDaoConfig;
    private final DaoConfig telefonosDaoConfig;
    private final DaoConfig institucionDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig comunaDaoConfig;
    private final DaoConfig tareaDaoConfig;
    private final DaoConfig accionDaoConfig;
    private final DaoConfig motivoDaoConfig;
    private final DaoConfig motivoFiscaliaDaoConfig;
    private final DaoConfig tipoVehiculoDaoConfig;
    private final DaoConfig firmaDaoConfig;
    private final DaoConfig logsDaoConfig;

    private final FinalizarActaDao finalizarActaDao;
    private final ActaDao actaDao;
    private final VehiculoDataDao vehiculoDataDao;
    private final VehiculoDao vehiculoDao;
    private final FichaEstadoVisualDao fichaEstadoVisualDao;
    private final EstadoVisualDao estadoVisualDao;
    private final ParqueaderoSummaryDao parqueaderoSummaryDao;
    private final EspeciasDao especiasDao;
    private final ClienteDao clienteDao;
    private final ParqueaderoDao parqueaderoDao;
    private final AgrupadorDao agrupadorDao;
    private final DireccionDao direccionDao;
    private final AutoridadDao autoridadDao;
    private final PersonaDao personaDao;
    private final CorreosDao correosDao;
    private final TelefonosDao telefonosDao;
    private final InstitucionDao institucionDao;
    private final UserDao userDao;
    private final ComunaDao comunaDao;
    private final TareaDao tareaDao;
    private final AccionDao accionDao;
    private final MotivoDao motivoDao;
    private final MotivoFiscaliaDao motivoFiscaliaDao;
    private final TipoVehiculoDao tipoVehiculoDao;
    private final FirmaDao firmaDao;
    private final LogsDao logsDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        finalizarActaDaoConfig = daoConfigMap.get(FinalizarActaDao.class).clone();
        finalizarActaDaoConfig.initIdentityScope(type);

        actaDaoConfig = daoConfigMap.get(ActaDao.class).clone();
        actaDaoConfig.initIdentityScope(type);

        vehiculoDataDaoConfig = daoConfigMap.get(VehiculoDataDao.class).clone();
        vehiculoDataDaoConfig.initIdentityScope(type);

        vehiculoDaoConfig = daoConfigMap.get(VehiculoDao.class).clone();
        vehiculoDaoConfig.initIdentityScope(type);

        fichaEstadoVisualDaoConfig = daoConfigMap.get(FichaEstadoVisualDao.class).clone();
        fichaEstadoVisualDaoConfig.initIdentityScope(type);

        estadoVisualDaoConfig = daoConfigMap.get(EstadoVisualDao.class).clone();
        estadoVisualDaoConfig.initIdentityScope(type);

        parqueaderoSummaryDaoConfig = daoConfigMap.get(ParqueaderoSummaryDao.class).clone();
        parqueaderoSummaryDaoConfig.initIdentityScope(type);

        especiasDaoConfig = daoConfigMap.get(EspeciasDao.class).clone();
        especiasDaoConfig.initIdentityScope(type);

        clienteDaoConfig = daoConfigMap.get(ClienteDao.class).clone();
        clienteDaoConfig.initIdentityScope(type);

        parqueaderoDaoConfig = daoConfigMap.get(ParqueaderoDao.class).clone();
        parqueaderoDaoConfig.initIdentityScope(type);

        agrupadorDaoConfig = daoConfigMap.get(AgrupadorDao.class).clone();
        agrupadorDaoConfig.initIdentityScope(type);

        direccionDaoConfig = daoConfigMap.get(DireccionDao.class).clone();
        direccionDaoConfig.initIdentityScope(type);

        autoridadDaoConfig = daoConfigMap.get(AutoridadDao.class).clone();
        autoridadDaoConfig.initIdentityScope(type);

        personaDaoConfig = daoConfigMap.get(PersonaDao.class).clone();
        personaDaoConfig.initIdentityScope(type);

        correosDaoConfig = daoConfigMap.get(CorreosDao.class).clone();
        correosDaoConfig.initIdentityScope(type);

        telefonosDaoConfig = daoConfigMap.get(TelefonosDao.class).clone();
        telefonosDaoConfig.initIdentityScope(type);

        institucionDaoConfig = daoConfigMap.get(InstitucionDao.class).clone();
        institucionDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        comunaDaoConfig = daoConfigMap.get(ComunaDao.class).clone();
        comunaDaoConfig.initIdentityScope(type);

        tareaDaoConfig = daoConfigMap.get(TareaDao.class).clone();
        tareaDaoConfig.initIdentityScope(type);

        accionDaoConfig = daoConfigMap.get(AccionDao.class).clone();
        accionDaoConfig.initIdentityScope(type);

        motivoDaoConfig = daoConfigMap.get(MotivoDao.class).clone();
        motivoDaoConfig.initIdentityScope(type);

        motivoFiscaliaDaoConfig = daoConfigMap.get(MotivoFiscaliaDao.class).clone();
        motivoFiscaliaDaoConfig.initIdentityScope(type);

        tipoVehiculoDaoConfig = daoConfigMap.get(TipoVehiculoDao.class).clone();
        tipoVehiculoDaoConfig.initIdentityScope(type);

        firmaDaoConfig = daoConfigMap.get(FirmaDao.class).clone();
        firmaDaoConfig.initIdentityScope(type);

        logsDaoConfig = daoConfigMap.get(LogsDao.class).clone();
        logsDaoConfig.initIdentityScope(type);

        finalizarActaDao = new FinalizarActaDao(finalizarActaDaoConfig, this);
        actaDao = new ActaDao(actaDaoConfig, this);
        vehiculoDataDao = new VehiculoDataDao(vehiculoDataDaoConfig, this);
        vehiculoDao = new VehiculoDao(vehiculoDaoConfig, this);
        fichaEstadoVisualDao = new FichaEstadoVisualDao(fichaEstadoVisualDaoConfig, this);
        estadoVisualDao = new EstadoVisualDao(estadoVisualDaoConfig, this);
        parqueaderoSummaryDao = new ParqueaderoSummaryDao(parqueaderoSummaryDaoConfig, this);
        especiasDao = new EspeciasDao(especiasDaoConfig, this);
        clienteDao = new ClienteDao(clienteDaoConfig, this);
        parqueaderoDao = new ParqueaderoDao(parqueaderoDaoConfig, this);
        agrupadorDao = new AgrupadorDao(agrupadorDaoConfig, this);
        direccionDao = new DireccionDao(direccionDaoConfig, this);
        autoridadDao = new AutoridadDao(autoridadDaoConfig, this);
        personaDao = new PersonaDao(personaDaoConfig, this);
        correosDao = new CorreosDao(correosDaoConfig, this);
        telefonosDao = new TelefonosDao(telefonosDaoConfig, this);
        institucionDao = new InstitucionDao(institucionDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        comunaDao = new ComunaDao(comunaDaoConfig, this);
        tareaDao = new TareaDao(tareaDaoConfig, this);
        accionDao = new AccionDao(accionDaoConfig, this);
        motivoDao = new MotivoDao(motivoDaoConfig, this);
        motivoFiscaliaDao = new MotivoFiscaliaDao(motivoFiscaliaDaoConfig, this);
        tipoVehiculoDao = new TipoVehiculoDao(tipoVehiculoDaoConfig, this);
        firmaDao = new FirmaDao(firmaDaoConfig, this);
        logsDao = new LogsDao(logsDaoConfig, this);

        registerDao(FinalizarActa.class, finalizarActaDao);
        registerDao(Acta.class, actaDao);
        registerDao(VehiculoData.class, vehiculoDataDao);
        registerDao(Vehiculo.class, vehiculoDao);
        registerDao(FichaEstadoVisual.class, fichaEstadoVisualDao);
        registerDao(EstadoVisual.class, estadoVisualDao);
        registerDao(ParqueaderoSummary.class, parqueaderoSummaryDao);
        registerDao(Especias.class, especiasDao);
        registerDao(Cliente.class, clienteDao);
        registerDao(Parqueadero.class, parqueaderoDao);
        registerDao(Agrupador.class, agrupadorDao);
        registerDao(Direccion.class, direccionDao);
        registerDao(Autoridad.class, autoridadDao);
        registerDao(Persona.class, personaDao);
        registerDao(Correos.class, correosDao);
        registerDao(Telefonos.class, telefonosDao);
        registerDao(Institucion.class, institucionDao);
        registerDao(User.class, userDao);
        registerDao(Comuna.class, comunaDao);
        registerDao(Tarea.class, tareaDao);
        registerDao(Accion.class, accionDao);
        registerDao(Motivo.class, motivoDao);
        registerDao(MotivoFiscalia.class, motivoFiscaliaDao);
        registerDao(TipoVehiculo.class, tipoVehiculoDao);
        registerDao(Firma.class, firmaDao);
        registerDao(Logs.class, logsDao);
    }
    
    public void clear() {
        finalizarActaDaoConfig.getIdentityScope().clear();
        actaDaoConfig.getIdentityScope().clear();
        vehiculoDataDaoConfig.getIdentityScope().clear();
        vehiculoDaoConfig.getIdentityScope().clear();
        fichaEstadoVisualDaoConfig.getIdentityScope().clear();
        estadoVisualDaoConfig.getIdentityScope().clear();
        parqueaderoSummaryDaoConfig.getIdentityScope().clear();
        especiasDaoConfig.getIdentityScope().clear();
        clienteDaoConfig.getIdentityScope().clear();
        parqueaderoDaoConfig.getIdentityScope().clear();
        agrupadorDaoConfig.getIdentityScope().clear();
        direccionDaoConfig.getIdentityScope().clear();
        autoridadDaoConfig.getIdentityScope().clear();
        personaDaoConfig.getIdentityScope().clear();
        correosDaoConfig.getIdentityScope().clear();
        telefonosDaoConfig.getIdentityScope().clear();
        institucionDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
        comunaDaoConfig.getIdentityScope().clear();
        tareaDaoConfig.getIdentityScope().clear();
        accionDaoConfig.getIdentityScope().clear();
        motivoDaoConfig.getIdentityScope().clear();
        motivoFiscaliaDaoConfig.getIdentityScope().clear();
        tipoVehiculoDaoConfig.getIdentityScope().clear();
        firmaDaoConfig.getIdentityScope().clear();
        logsDaoConfig.getIdentityScope().clear();
    }

    public FinalizarActaDao getFinalizarActaDao() {
        return finalizarActaDao;
    }

    public ActaDao getActaDao() {
        return actaDao;
    }

    public VehiculoDataDao getVehiculoDataDao() {
        return vehiculoDataDao;
    }

    public VehiculoDao getVehiculoDao() {
        return vehiculoDao;
    }

    public FichaEstadoVisualDao getFichaEstadoVisualDao() {
        return fichaEstadoVisualDao;
    }

    public EstadoVisualDao getEstadoVisualDao() {
        return estadoVisualDao;
    }

    public ParqueaderoSummaryDao getParqueaderoSummaryDao() {
        return parqueaderoSummaryDao;
    }

    public EspeciasDao getEspeciasDao() {
        return especiasDao;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public ParqueaderoDao getParqueaderoDao() {
        return parqueaderoDao;
    }

    public AgrupadorDao getAgrupadorDao() {
        return agrupadorDao;
    }

    public DireccionDao getDireccionDao() {
        return direccionDao;
    }

    public AutoridadDao getAutoridadDao() {
        return autoridadDao;
    }

    public PersonaDao getPersonaDao() {
        return personaDao;
    }

    public CorreosDao getCorreosDao() {
        return correosDao;
    }

    public TelefonosDao getTelefonosDao() {
        return telefonosDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public InstitucionDao getInstitucionDao() {
        return institucionDao;
    }

    public ComunaDao getComunaDao() {
        return comunaDao;
    }

    public TareaDao getTareaDao() {
        return tareaDao;
    }

    public AccionDao getAccionDao() {
        return accionDao;
    }

    public FirmaDao getFirmaDao() {
        return firmaDao;
    }

    public LogsDao getLogsDao() {
        return logsDao;
    }
}
