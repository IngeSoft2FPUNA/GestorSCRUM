------------------------------------------------------
CREATE FUNCTION validar_sprint() returns trigger as $$
    DECLARE
        sprint_count int;
        active_count int;
    BEGIN
        active_count := (SELECT COUNT(*) 
                            FROM sprint 
                        WHERE sprint.f_fin IS NULL
                            AND sprint.id_proyecto = NEW.id_proyecto
        );
        sprint_count := (SELECT COUNT(*) 
                            FROM sprint 
                        WHERE sprint.id_sprint != NEW.id_sprint 
                            AND sprint.f_ini <= NEW.f_ini 
                            AND (sprint.f_fin > NEW.f_fin OR sprint.f_fin IS NULL)
                            AND sprint.id_proyecto = NEW.id_proyecto
        );
        IF (sprint_count != 0) THEN
            RAISE EXCEPTION 'El periodo entre % y % ya tiene un sprint asignado', NEW.f_ini, NEW.f_fin;
        END IF;
        IF (active_count != 0) THEN
            RAISE EXCEPTION 'Ya hay un sprint activo dentro del proyecto %', NEW.id_proyecto;
        END IF;
        RETURN NEW;
    END;
    $$ language plpgsql;

CREATE TRIGGER validar_sprint_trigger BEFORE INSERT OR UPDATE ON sprint
    FOR EACH ROW EXECUTE PROCEDURE validar_sprint();
------------------------------------------------------
/*CREATE FUNCTION validar_userstories() returns trigger as $$
    DECLARE
    BEGIN
    END
    $$ language plpgsql;*/